package Shotmd.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.powerShell
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import _Self.utils.DeploymentBuilders

object Shotmd_Build : BuildType({
    DeploymentBuilders.createQtWindowsBuild(
        name = "Build",
        qtInstallPath = "C:/Qt/6.9.1/msvc2022_64",
        buildConfig = "Release",
        executableName = "Shotmd"
    )(this)
    
    vcs {
        root(Shotmd.vcsRoots.Shotmd_GitGithubComK88936ShotmdGitRefsHeadsMaster)
    }
})
