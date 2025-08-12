package Bubble

import Bubble.buildTypes.*
import Bubble.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("Bubble")
    name = "Bubble"

    vcsRoot(Bubble_GitGithubComK88936BubbleGitRefsHeadsMain)

    buildType(Bubble_Build)
})
