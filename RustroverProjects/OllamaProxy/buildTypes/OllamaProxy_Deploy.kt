package RustroverProjects.OllamaProxy.buildTypes

import RustroverProjects.OllamaProxy.vcsRoots.OllamaProxy_GitGithubComK88936ollamaProxyGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import Utils.GithubReleaseDeployTemplate.createGithubReleaseDeployment


object OllamaProxy_Deploy : BuildType({
    id ("OllamaProxy_Deploy")
    name ="Deploy"
    
    triggers {
        finishBuildTrigger {
            buildType = "OllamaProxy_BuildWin"
            successfulOnly = true
        }
    }

    dependencies {

        artifacts(OllamaProxy_Build) {
            buildRule = lastSuccessful()
            artifactRules = "*=>_deploy/"
        }
    }

   createGithubReleaseDeployment(
        vcsRoot = OllamaProxy_GitGithubComK88936ollamaProxyGitRefsHeadsMain,
        assetsPath = "_deploy/*"
    )(this)


})