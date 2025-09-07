package UnityProjects.Bubble

import UnityProjects.Bubble.buildTypes.*
import UnityProjects.Bubble.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("Bubble")
    name = "Bubble"

    vcsRoot(Bubble_GitGithubComK88936BubbleGitRefsHeadsMain)

    buildType(Bubble_Build)
})
