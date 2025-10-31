package RustroverProjects.TunetRust.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object TunetRust_GitGithubComK88936tunetRustGitRefsHeadsMaster : GitVcsRoot({
    id("TunetRust_GitGithubComK88936tunetRustGitRefsHeadsMaster")
    name = "git@github.com:k88936/tunet-rust.git#refs/heads/master"
    url = "git@github.com:k88936/tunet-rust.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})
