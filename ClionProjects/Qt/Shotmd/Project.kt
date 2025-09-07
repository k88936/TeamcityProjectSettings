package ClionProjects.Qt.Shotmd

import ClionProjects.Qt.Shotmd.buildTypes.*
import ClionProjects.Qt.Shotmd.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("Shotmd")
    name = "Shotmd"

    vcsRoot(Shotmd_GitGithubComK88936ShotmdGitRefsHeadsMaster)

    buildType(Shotmd_Build)
    buildType(Shotmd_Deploy)
})
