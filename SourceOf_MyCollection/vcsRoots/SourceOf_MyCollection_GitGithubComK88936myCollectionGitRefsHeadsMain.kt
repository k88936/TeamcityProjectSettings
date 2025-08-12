package SourceOf_MyCollection.vcsRoots

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object SourceOf_MyCollection_GitGithubComK88936myCollectionGitRefsHeadsMain : GitVcsRoot({
    name = "git@github.com:k88936/my-collection.git#refs/heads/main"
    url = "git@github.com:k88936/my-collection.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "k99836 github"
    }
})
