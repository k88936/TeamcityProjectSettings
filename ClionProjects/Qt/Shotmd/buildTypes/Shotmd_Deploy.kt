package ClionProjects.Qt.Shotmd.buildTypes

import ClionProjects.Qt.Shotmd.vcsRoots.Shotmd_GitGithubComK88936shotmdGitRefsHeadsMaster
import Utils.Deploy.applyGithubReleaseDeployment
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger

object Shotmd_Deploy : BuildType({
    id("Shotmd_Deploy")
    name = "Deploy"
    type = Type.DEPLOYMENT

    triggers {
        finishBuildTrigger {
            buildType = Shotmd_Build.id?.value
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


    vcs {
        root(Shotmd_GitGithubComK88936shotmdGitRefsHeadsMaster)
    }
    applyGithubReleaseDeployment(
        assetsPath = "_deploy/*",
    )
})