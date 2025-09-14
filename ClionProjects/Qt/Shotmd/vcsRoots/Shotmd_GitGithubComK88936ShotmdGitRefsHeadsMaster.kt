package ClionProjects.Qt.Shotmd.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object Shotmd_GitGithubComK88936shotmdGitRefsHeadsMaster : GitVcsRoot({
    name = "git@github.com:k88936/shotmd.git#refs/heads/master"
    url = "git@github.com:k88936/shotmd.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})
