package Fernflower.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object Fernflower_GitGithubComK88936fernflowerGitRefsHeadsMaster : GitVcsRoot({
    name = "git@github.com:k88936/fernflower.git#refs/heads/master"
    url = "git@github.com:k88936/fernflower.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})
