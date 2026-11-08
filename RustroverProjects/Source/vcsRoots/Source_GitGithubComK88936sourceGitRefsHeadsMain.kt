package RustroverProjects.Source.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object Source_GitGithubComK88936sourceGitRefsHeadsMain : GitVcsRoot({
    name = "git@github.com:k88936/Source.git#refs/heads/main"
    url = "git@github.com:k88936/Source.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/main"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})
