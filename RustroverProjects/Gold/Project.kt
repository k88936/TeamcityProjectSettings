package RustroverProjects.Gold

import RustroverProjects.Gold.buildTypes.*
import RustroverProjects.Gold.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("Gold")
    name = "Gold"

    vcsRoot(Gold_GitGithubComK88936goldGitRefsHeadsMain)

    buildType(Gold_BuildLinux)
    buildType(Gold_BuildWin)
})
