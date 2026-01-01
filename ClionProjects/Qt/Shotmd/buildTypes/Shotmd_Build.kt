package ClionProjects.Qt.Shotmd.buildTypes

import ClionProjects.Qt.applyQtWindowsBuild
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon

object Shotmd_Build : BuildType({
    applyQtWindowsBuild(
        name = "Build",
        installPath = "C:\\Qt\\6.10.0\\msvc2022_64",
        buildConfig = "Release",
        executableName = "shotmd"
    )
    
    vcs {
        root(ClionProjects.Qt.Shotmd.vcsRoots.Shotmd_GitGithubComK88936shotmdGitRefsHeadsMaster)
    }
    features{
        perfmon {  }
    }
})
