package Shotmd.vcsRoots

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object Shotmd_GitGithubComK88936ShotmdGitRefsHeadsMaster : GitVcsRoot({
    name = "git@github.com:k88936/Shotmd.git#refs/heads/master"
    url = "git@github.com:k88936/Shotmd.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "k99836 github"
    }
})
