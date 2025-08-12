package SourceOf.vcsRoots

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object SourceOf_GitGithubComK88936sourceOfGitRefsHeadsMain : GitVcsRoot({
    name = "git@github.com:k88936/source-of.git#refs/heads/main"
    url = "git@github.com:k88936/source-of.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "k99836 github"
    }
})
