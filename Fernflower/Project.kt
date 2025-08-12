package Fernflower

import Fernflower.buildTypes.*
import Fernflower.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("Fernflower")
    name = "Fernflower"

    vcsRoot(Fernflower_GitGithubComK88936fernflowerGitRefsHeadsMaster)

    buildType(Fernflower_Build)
    buildType(Fernflower_Deploy)
})
