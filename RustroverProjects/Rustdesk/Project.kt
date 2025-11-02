package RustroverProjects.Rustdesk

import RustroverProjects.Rustdesk.buildTypes.Rustdesk_Build
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("Rustdesk")
    name = "Rustdesk"

    vcsRoot(RustroverProjects.Rustdesk.vcsRoots.Rustdesk_GitGithubComK88936rustdeskGitRefsHeadsMaster)
    buildType(Rustdesk_Build)
})