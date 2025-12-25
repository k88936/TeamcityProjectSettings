package ClionProjects.Qt.Shotmd

import ClionProjects.Qt.Shotmd.buildTypes.Shotmd_Build
import ClionProjects.Qt.Shotmd.buildTypes.Shotmd_Deploy
import ClionProjects.Qt.Shotmd.vcsRoots.Shotmd_GitGithubComK88936shotmdGitRefsHeadsMaster
import ClionProjects.Qt.shotmd.buildTypes.Shotmd_Scoop
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("Shotmd")
    name = "Shotmd"

    vcsRoot(Shotmd_GitGithubComK88936shotmdGitRefsHeadsMaster)

    buildType(Shotmd_Build)
    buildType(Shotmd_Deploy)
    buildType(Shotmd_Scoop)
})
