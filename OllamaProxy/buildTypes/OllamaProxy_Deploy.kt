package OllamaProxy.buildTypes

import OllamaProxy.vcsRoots.OllamaProxy_GitGithubComK88936ollamaProxyGitRefsHeadsMain
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
        vcsRoot = OllamaProxy_GitGithubComK88936ollamaProxyGitRefsHeadsMain,
        assetsPath = "_deploy/*"
    )(this)


})