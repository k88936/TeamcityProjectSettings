package RustroverProjects.Trackit

import RustroverProjects.Trackit.buildTypes.Trackit_Build
import RustroverProjects.Trackit.buildTypes.Trackit_Deploy
import RustroverProjects.Trackit.vcsRoots.Trackit_GitGithubComK88936trackitGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("Trackit")
    name = "Trackit"

    vcsRoot(Trackit_GitGithubComK88936trackitGitRefsHeadsMain)

    buildType(Trackit_Build)
    buildType(Trackit_Deploy)
})
