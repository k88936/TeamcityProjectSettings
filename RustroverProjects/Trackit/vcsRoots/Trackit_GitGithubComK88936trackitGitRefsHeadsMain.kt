package RustroverProjects.Trackit.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object Trackit_GitGithubComK88936trackitGitRefsHeadsMain : GitVcsRoot({
    name = "git@github.com:k88936/trackit.git#refs/heads/main"
    url = "git@github.com:k88936/trackit.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})

