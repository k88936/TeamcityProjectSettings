package OllamaProxy.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import utils.DeploymentBuilders


object OllamaProxy_Deploy : BuildType({
    id ("OllamaProxy_Deploy")
    name ="Deploy"
    DeploymentBuilders.createGithubReleaseDeployment(
        tagPattern = "v%build.number%",
        generateNotes = true,
        notes = "Deploying OllamaProxy application via GitHub Release",
        assetsPath = "_deploy/*"
    )(this)


})