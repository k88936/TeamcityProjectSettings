package Fernflower.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import utils.DeploymentBuilders

object Fernflower_Deploy : BuildType({
    DeploymentBuilders.createGithubReleaseDeployment(
        vcsRoot = Fernflower.vcsRoots.Fernflower_GitGithubComK88936fernflowerGitRefsHeadsMaster,
        assetsPath = "_deploy/*"
    )(this)
    

    triggers {
        finishBuildTrigger {
            buildType = "${Fernflower_Build.id}"
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(Fernflower_Build) {
            buildRule = lastSuccessful()
            artifactRules = "*=>_deploy/"
        }
    }

})