package SourceOf_MyCollection

import SourceOf_MyCollection.buildTypes.*
import SourceOf_MyCollection.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("SourceOf_MyCollection")
    name = "DeployPage"

    vcsRoot(SourceOf_MyCollection_GitGithubComK88936myCollectionGitRefsHeadsMain)

    buildType(SourceOf_Deploy)
})
