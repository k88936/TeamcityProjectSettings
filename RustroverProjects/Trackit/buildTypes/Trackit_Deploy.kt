package RustroverProjects.Trackit.buildTypes

import RustroverProjects.Trackit.vcsRoots.Trackit_GitGithubComK88936trackitGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import utils.deploy.applyGithubReleaseDeployment
import utils.deploy.applySourceOfDeployment

object Trackit_Deploy : BuildType({
    id("Trackit_Deploy")
    name = "deploy"
    type = Type.DEPLOYMENT

    vcs {
        root(Trackit_GitGithubComK88936trackitGitRefsHeadsMain)
    }

    triggers {
        finishBuildTrigger {
            buildType = Trackit_Build.id?.value
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(Trackit_Build) {
            buildRule = lastSuccessful()
            artifactRules = "*=>_deploy/"
        }
    }
    applySourceOfDeployment("trackit")
    applyGithubReleaseDeployment(
        assetsPath = "_deploy/*"
    )
    features {
        perfmon { }
    }
})

