package Shotmd.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import utils.DeploymentBuilders

object Shotmd_Deploy : BuildType({
    id("Shotmd_Deploy")
    name = "Deploy"
    description = "Deploys Shotmd application via GitHub Release"


    DeploymentBuilders.createGithubReleaseDeployment(
        assetsPath = "_deploy/*",
    )(this)
    
    vcs {
        root(Shotmd.vcsRoots.Shotmd_GitGithubComK88936ShotmdGitRefsHeadsMaster)
    }
    
    triggers {
        finishBuildTrigger {
            buildType = "${Shotmd_Build.id}"
            successfulOnly = true
        }
    }
    dependencies {
        artifacts(Shotmd_Build) {
            buildRule = lastSuccessful()
            artifactRules = "Shotmd.zip=>_deploy/"
        }
    }
})