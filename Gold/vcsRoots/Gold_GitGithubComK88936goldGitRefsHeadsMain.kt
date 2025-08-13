package Gold.vcsRoots

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object Gold_GitGithubComK88936goldGitRefsHeadsMain : GitVcsRoot({
    name = "git@github.com:k88936/gold.git#refs/heads/main"
    url = "git@github.com:k88936/gold.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})
