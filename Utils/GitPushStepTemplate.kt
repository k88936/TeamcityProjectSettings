package Utils

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script

object GitPushStepTemplate {

    /**
     * 创建一个Git推送构建步骤
     */
    fun createGitPushStep(comment: String = "update"): BuildSteps.() -> Unit {
        return {
            script {
                this.name = "Git Push Changes"
                id = "git_push"
                this.scriptContent = """
                    git config user.email "teamcity@kvto.dev"
                    git config user.name "teamcity"
                    export GIT_SSH_COMMAND="ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no"
                    git add -A
                    git commit -m"$comment"
                    git push --force
                """.trimIndent()
            }
        }
    }
}
