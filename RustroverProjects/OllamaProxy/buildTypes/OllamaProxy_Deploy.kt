package RustroverProjects.OllamaProxy.buildTypes

import RustroverProjects.OllamaProxy.vcsRoots.OllamaProxy_GitGithubComK88936ollamaProxyGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import utils.deploy.applyGithubReleaseDeployment
import utils.deploy.applySourceOfDeployment


object OllamaProxy_Deploy : BuildType({
    id("OllamaProxy_Deploy")
    name = "deploy"
    type = Type.DEPLOYMENT

    triggers {
        finishBuildTrigger {
            buildType = OllamaProxy_Build.id?.value
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(OllamaProxy_Build) {
            buildRule = lastSuccessful()
            artifactRules = "*=>_deploy/"
        }
    }

    vcs{
        root(OllamaProxy_GitGithubComK88936ollamaProxyGitRefsHeadsMain)
    }
    applySourceOfDeployment("ollama-proxy")
    applyGithubReleaseDeployment(
        assetsPath = "_deploy/*"
    )


})