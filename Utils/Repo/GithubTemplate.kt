package Utils.Repo

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
}