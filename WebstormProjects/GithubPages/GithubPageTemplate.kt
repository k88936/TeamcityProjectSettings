package WebstormProjects.GithubPages

import Utils.GitPushStepTemplate.createGitPushStep
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildFeatures.sshAgent
import jetbrains.buildServer.configs.kotlin.buildSteps.nodeJS
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.schedule
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

class GithubPageTemplate() : Project() {

    constructor(
        githubRepo: String,
        extraBuildCommand: String = "",
        extraBuildConfig: (BuildType) -> Unit = {}
    ) : this() {
        fun format(name: String): String {
            return name.replace("https?|[^a-zA-Z0-9]".toRegex(), "")
        }

        val formatedName = format(githubRepo)
        this.name = formatedName
        id(formatedName + "_Site")
        val root = GitVcsRoot {
            id(formatedName + "_vcsRoot")
            name = formatedName + "_vcsRoot"
            url = githubRepo
            branch = "refs/heads/main"
            branchSpec = "refs/heads/*"
            authMethod = uploadedKey {
                uploadedKey = "id_rsa"
            }
        }
        vcsRoot(root)

        val build = BuildType {
            id(formatedName + "_Build")
            name = "Build"

            vcs {
                root(root)
            }

            steps {
                script {
                    name = "ensure workflow"
                    scriptContent = DEPLOY_WORKFLOW
                }
                nodeJS {
                    shellScript =
                        """
                        source /etc/profile
                        npm ci
                        npm run build
                        """.trimIndent() + '\n' + extraBuildCommand.trimIndent()
                    dockerImage = "kvtodev/ci-containers:js"
                    dockerPull = true
                }
                createGitPushStep("[CI] Teamcity build pages")(this)
            }

            triggers {
                vcs {
                    triggerRules = """-:comment=\[CI\]:**"""
                }
            }

            features {
                sshAgent {
                    teamcitySshKey = "id_rsa"
                }
                perfmon {
                }
            }

            requirements {
            }
        }

        extraBuildConfig(build)
        buildType(build)
    }

    companion object {
        val DEPLOY_WORKFLOW = """
                            if [ -f .github/workflows/static.yaml ]; then exit 0; fi
                            mkdir -p .github/workflows
                            cat > .github/workflows/static.yaml << 'EOF'
                            # Simple workflow for deploying static content to GitHub Pages
                            name: Deploy static content to Pages

                            on:
                              # Runs on pushes targeting the default branch
                              push:
                                branches: ["main"]

                              # Allows you to run this workflow manually from the Actions tab
                              workflow_dispatch:

                            # Sets permissions of the GITHUB_TOKEN to allow deployment to GitHub Pages
                            permissions:
                              contents: read
                              pages: write
                              id-token: write

                            # Allow only one concurrent deployment, skipping runs queued between the run in-progress and latest queued.
                            # However, do NOT cancel in-progress runs as we want to allow these production deployments to complete.
                            concurrency:
                              group: "pages"
                              cancel-in-progress: false

                            jobs:
                              # Single deploy job since we're just deploying
                              deploy:
                                environment:
                                  name: github-pages
                                  url: ${'$'}{{ steps.deployment.outputs.page_url }}
                                runs-on: ubuntu-latest
                                steps:
                                  - name: Checkout
                                    uses: actions/checkout@v4
                                  - name: Setup Pages
                                    uses: actions/configure-pages@v5
                                  - name: Upload artifact
                                    uses: actions/upload-pages-artifact@v3
                                    with:
                                      # Upload entire repository
                                      path: './dist/'
                                  - name: Deploy to GitHub Pages
                                    id: deployment
                                    uses: actions/deploy-pages@v4
                            EOF
                        """.trimIndent()
    }
}
