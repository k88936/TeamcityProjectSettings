package Utils.Version

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.script

object GithubTemplate {

    /**
     * 创建一个Git推送构建步骤
     */
    fun createGitPushStep(comment: String = "update"): BuildType.() -> Unit {
        return {

            steps {
                script {
                    this.name = "Git Push Changes"
                    id = "git_push"
                    this.scriptContent = """
                        git config user.email "teamcity@k88936.top"
                        git config user.name "teamcity"
                        export GIT_SSH_COMMAND="ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no"
                        git add -A
                        git commit -m"[CI] $comment"
                        git push --force
                    """.trimIndent()
                }
            }
        }
    }

    fun createPRStep(
        branchName: String,
        comment: String,
        prMessage: String,
    ): BuildType.() -> Unit {
        return {
            steps {
                script {
                    name = "PR Step"
                    id = "github_pr"
                    scriptContent = """
                        #!/bin/bash
                        # Exit if no unstaged changes
                        if ! git diff --quiet; then
                            echo "Unstaged changes detected."

                            # Create a new branch with a timestamp-based name
                            branch_name="$branchName-$(date +%Y%m%d-%H%M%S)"
                            git checkout -b "${'$'}branch_name"

                            # Stage and commit all changes
                            git add .
                            git commit -m "[CI] $comment by Teamcity"

                            # Push to remote and create PR
                            git push -u origin "${'$'}branch_name"
                            gh pr create --title "$prMessage" --fill

                            echo "PR created from branch: ${'$'}branch_name"
                        else
                            echo "No unstaged changes. Exiting."
                            exit 0
                        fi
                    """.trimIndent()
                }
            }

            params {
                password("env.GH_TOKEN", "credentialsJSON:04d96fb0-dbf8-457b-be29-2327ab11dd68")
            }
            requirements {
                exists("env.GH_CLI")
            }
        }
    }
}