package Bubble.vcsRoots

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object Bubble_GitGithubComK88936BubbleGitRefsHeadsMain : GitVcsRoot({
    name = "git@github.com:k88936/Bubble.git#refs/heads/main"
    url = "git@github.com:k88936/Bubble.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    agentCleanFilesPolicy = GitVcsRoot.AgentCleanFilesPolicy.IGNORED_ONLY
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})
