package RustroverProjects.Citizen.buildTypes

import RustroverProjects.Citizen.vcsRoots.Citizen_GitGithubComK88936citizenGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import utils.deploy.applyGithubReleaseDeployment
import utils.deploy.applySourceOfDeployment

object Citizen_Deploy : BuildType({
    id("Citizen_Deploy")
    name = "deploy"
    type = Type.DEPLOYMENT

    vcs {
        root(Citizen_GitGithubComK88936citizenGitRefsHeadsMain)
    }

    triggers {
        finishBuildTrigger {
            buildType = Citizen_Build.id?.value
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(Citizen_Build) {
            buildRule = lastSuccessful()
            artifactRules = "*=>_deploy/"
        }
    }
    applySourceOfDeployment("ollama-proxy")
    applyGithubReleaseDeployment(
        assetsPath = "_deploy/*"
    )
    features {
        perfmon { }
    }
})
