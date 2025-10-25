package RustroverProjects.Gold.buildTypes

import RustroverProjects.Gold.vcsRoots.Gold_GitGithubComK88936goldGitRefsHeadsMain
import RustroverProjects.OllamaProxy.buildTypes.OllamaProxy_Build
import Utils.SourceOfDeployTemplate
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger

object Gold_Deploy : BuildType({
    id("Gold_Deploy")
    name = "Deploy"
    type = Type.DEPLOYMENT


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

    SourceOfDeployTemplate.createSourceOfDeployment(
        name = "gold",
        tagPattern = "v1.0.0",
        assets = "_deploy/gold"
    )(this)


    features{
        perfmon {  }
    }
})
