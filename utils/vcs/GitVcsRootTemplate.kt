package utils.vcs

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot.AgentCleanFilesPolicy

fun GitVcsRootTemplate(
    url: String,
    branch: String = "refs/heads/main",
    branchSpec: String = "refs/heads/*",
    sshKeyId: String = "id_rsa",
    cleanPolicy: AgentCleanFilesPolicy = AgentCleanFilesPolicy.IGNORED_ONLY
): GitVcsRoot {
    return GitVcsRoot({
        id("${url.replace(':', '#')}#$branch")
        this.name = "$url#$branch"
        this.url = url
        this.branch = branch
        this.branchSpec = branchSpec
        agentCleanFilesPolicy = cleanPolicy
        authMethod = uploadedKey {
            uploadedKey = sshKeyId
        }
    })
}