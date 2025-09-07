package WebstormProjects.GithubPages

import Utils.GitPushStepTemplate.createGitPushStep
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildFeatures.sshAgent
import jetbrains.buildServer.configs.kotlin.buildSteps.nodeJS
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
                nodeJS {
                    id = "nodejs_runner"
                    shellScript =
                        ("""
                                    npm install
                                    npm run build
                                """
                                + extraBuildCommand)
                            .trimIndent()
                }
                createGitPushStep("build pages")(this)
            }

            triggers {
                schedule {
                    triggerBuild = always()
                    withPendingChangesOnly = false
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
}
