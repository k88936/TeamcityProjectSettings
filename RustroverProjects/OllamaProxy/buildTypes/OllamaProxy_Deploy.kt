package RustroverProjects.OllamaProxy.buildTypes

import RustroverProjects.OllamaProxy.vcsRoots.OllamaProxy_GitGithubComK88936ollamaProxyGitRefsHeadsMain
import Utils.Deploy.GithubReleaseDeployTemplate.createGithubReleaseDeployment
import Utils.Deploy.SourceOfDeployTemplate
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger


object OllamaProxy_Deploy : BuildType({
    id("OllamaProxy_Deploy")
    name = "Deploy"
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
    SourceOfDeployTemplate.createSourceOfDeployment("ollama-proxy")(this)
    createGithubReleaseDeployment(
        assetsPath = "_deploy/*"
    )(this)


})