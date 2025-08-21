package GithubPage.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object GithubPage_GitGithubComK88936k88936githubIoGit : GitVcsRoot({
    id("GitGithubComK88936k88936githubIoGit_GitGithubComK88936k88936githubIoGit")
    name = "git@github.com:k88936/k88936.github.io.git"
    url = "git@github.com:k88936/k88936.github.io.git"
    branch = "refs/heads/main"
    authMethod = uploadedKey {
        userName = "git"
        uploadedKey = "id_rsa"
    }
})