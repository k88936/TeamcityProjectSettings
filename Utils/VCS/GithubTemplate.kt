package Utils.VCS

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.sshAgent
import jetbrains.buildServer.configs.kotlin.buildSteps.script

object GithubTemplate {
    const val BYPASS_SSH_KEY_CHECK =
        "export GIT_SSH_COMMAND=\"ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no\""
    const val USER_INFO = "git config user.email \"Teamcity@k88936.top\" && git config user.name \"Teamcity\""

}

/**
 * 创建一个Git推送构建步骤
 */
fun BuildType.applyGitPushStep(comment: String = "update") {
    steps {
        script {
            this.name = "Git Push Changes"
            id = "git_push"
            this.scriptContent = """
                ${GithubTemplate.USER_INFO}
                ${GithubTemplate.BYPASS_SSH_KEY_CHECK}
                git add -A
                git commit -m"[CI] $comment"
                git push --force
            """.trimIndent()
        }
    }
    features {
        sshAgent {
            teamcitySshKey = "id_rsa"
        }
    }
}

fun BuildType.applyPRStep(
    branchName: String,
    body: String,
    title: String,
) {
    steps {
        script {
            name = "PR Step"
            id = "github_pr"
            scriptContent = """
                #!/bin/bash
                if git status --porcelain --branch | grep -q "ahead"; then
                    echo "Local commits detected ahead of remote."

                    branch_name="$branchName-%build.number%"
                    git checkout -b "${'$'}branch_name"
                    
                    ${GithubTemplate.BYPASS_SSH_KEY_CHECK}
                    git push -u origin "${'$'}branch_name"

                    gh pr create --title "$title" --body "$body" --base %teamcity.build.branch%

                    echo "PR created from branch: ${'$'}branch_name"
                else
                    echo "No local commits ahead of remote. Exiting."
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
    features {
        sshAgent {
            teamcitySshKey = "id_rsa"
        }
    }
}