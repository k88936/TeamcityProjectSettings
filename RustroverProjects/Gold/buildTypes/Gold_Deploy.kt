package RustroverProjects.Gold.buildTypes

import RustroverProjects.Gold.vcsRoots.Gold_GitGithubComK88936goldGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import utils.deploy.applySourceOfDeployment

object Gold_Deploy : BuildType({
    id("Gold_Deploy")
    name = "deploy"
    type = Type.DEPLOYMENT

    vcs {
        root(Gold_GitGithubComK88936goldGitRefsHeadsMain)
    }

    triggers {
        finishBuildTrigger {
            buildType = Gold_Build.id?.value
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(Gold_Build) {
            buildRule = lastSuccessful()
            artifactRules = "*=>_deploy/"
        }
    }

    applySourceOfDeployment(
        name = "gold",
        tagPattern = "latest",
        assets = "_deploy/gold"
    )

    features {
        perfmon { }
    }
})
