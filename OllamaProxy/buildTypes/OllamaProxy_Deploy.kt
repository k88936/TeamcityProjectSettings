package OllamaProxy.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import utils.DeploymentBuilders


object OllamaProxy_Deploy : BuildType({
    id ("OllamaProxy_Deploy")
    name ="Deploy"
    
    triggers {
        finishBuildTrigger {
            buildType = "OllamaProxy_BuildLinux"
            successfulOnly = true
        }
        finishBuildTrigger {
            buildType = "OllamaProxy_BuildWin"
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(OllamaProxy_BuildLinux) {
            buildRule = lastSuccessful()
            artifactRules = "*=>_deploy/"
        }

        artifacts(OllamaProxy_BuildWin) {
            buildRule = lastSuccessful()
            artifactRules = "*=>_deploy/"
        }
    }

    DeploymentBuilders.createGithubReleaseDeployment(
        tagPattern = "v%build.number%",
        generateNotes = true,
        notes = "Deploying OllamaProxy application via GitHub Release",
        assetsPath = "_deploy/*"
    )(this)


})