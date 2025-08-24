package SourceOf

import SourceOf.buildTypes.*
import SourceOf.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("SourceOf")
    name = "Source Of"

    vcsRoot(SourceOf_GitGithubComK88936sourceOfGitRefsHeadsMain)

    buildType(SourceOf_Build)

})
