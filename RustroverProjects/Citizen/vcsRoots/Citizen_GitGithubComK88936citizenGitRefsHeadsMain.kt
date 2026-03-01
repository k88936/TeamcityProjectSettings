package RustroverProjects.Citizen.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object Citizen_GitGithubComK88936citizenGitRefsHeadsMain : GitVcsRoot({
    name = "git@github.com:k88936/citizen.git#refs/heads/main"
    url = "git@github.com:k88936/citizen.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})
