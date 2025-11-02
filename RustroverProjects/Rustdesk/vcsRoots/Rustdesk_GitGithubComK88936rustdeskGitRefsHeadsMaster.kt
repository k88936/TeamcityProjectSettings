package RustroverProjects.Rustdesk.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object Rustdesk_GitGithubComK88936rustdeskGitRefsHeadsMaster : GitVcsRoot({
    id("Rustdesk_GitGithubComK88936rustdeskGitRefsHeadsMaster")
    name = "git@github.com:k88936/rustdesk.git#refs/heads/master"
    url = "git@github.com:k88936/rustdesk.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})