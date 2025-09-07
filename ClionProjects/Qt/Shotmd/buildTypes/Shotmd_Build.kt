package ClionProjects.Qt.Shotmd.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import Utils.QtWindowsBuildTemplate

object Shotmd_Build : BuildType({
    QtWindowsBuildTemplate.createQtWindowsBuild(
        name = "Build",
        qtInstallPath = "C:\\Qt\\6.9.1\\msvc2022_64",
        buildConfig = "Release",
        executableName = "Shotmd"
    )(this)
    
    vcs {
        root(ClionProjects.Qt.Shotmd.vcsRoots.Shotmd_GitGithubComK88936ShotmdGitRefsHeadsMaster)
    }
    features{
        perfmon {  }
    }
})
