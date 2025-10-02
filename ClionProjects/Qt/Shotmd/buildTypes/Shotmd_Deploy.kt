package ClionProjects.Qt.Shotmd.buildTypes

import ClionProjects.Qt.Shotmd.vcsRoots.Shotmd_GitGithubComK88936shotmdGitRefsHeadsMaster
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import Utils.GithubReleaseDeployTemplate.createGithubReleaseDeployment

object Shotmd_Deploy : BuildType({
    id("Shotmd_Deploy")
    name = "Deploy"

    triggers {
        finishBuildTrigger {
            buildType = "${Shotmd_Build.id}"
            successfulOnly = true
        }
    }
    features {
        perfmon { }
    }
    dependencies {
        artifacts(Shotmd_Build) {
            buildRule = lastSuccessful()
            artifactRules = "*=>_deploy/"
        }
    }

    createGithubReleaseDeployment(
        vcsRoot = Shotmd_GitGithubComK88936shotmdGitRefsHeadsMaster,
        assetsPath = "_deploy/*",
    )(this)
})