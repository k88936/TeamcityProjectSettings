package Shotmd.buildTypes

import Shotmd.vcsRoots.Shotmd_GitGithubComK88936ShotmdGitRefsHeadsMaster
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildFeatures.sshAgent
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import utils.DeploymentBuilders

object Shotmd_Deploy : BuildType({
    id("Shotmd_Deploy")
    name = "Deploy"
    description = "Deploys Shotmd application via GitHub Release"


    DeploymentBuilders.createGithubReleaseDeployment(
        vcsRoot = Shotmd_GitGithubComK88936ShotmdGitRefsHeadsMaster,
        assetsPath = "_deploy/*",
    )(this)

    triggers {
        finishBuildTrigger {
            buildType = "${Shotmd_Build.id}"
            successfulOnly = true
        }
    }
    features{
        perfmon {  }
    }
    dependencies {
        artifacts(Shotmd_Build) {
            buildRule = lastSuccessful()
            artifactRules = "Shotmd.zip=>_deploy/"
        }
    }
})