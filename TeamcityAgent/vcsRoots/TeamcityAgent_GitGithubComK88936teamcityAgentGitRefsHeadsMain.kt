package TeamcityAgent.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object TeamcityAgent_GitGithubComK88936teamcityAgentGitRefsHeadsMain : GitVcsRoot({
    id("TeamcityAgent_GitGithubComK88936teamcityAgentGitRefsHeadsMain")
    name = "git@github.com:k88936/teamcity-agent.git#refs/heads/main"
    url = "git@github.com:k88936/teamcity-agent.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})