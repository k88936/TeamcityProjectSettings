package Fernflower.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import utils.DeploymentBuilders

object Fernflower_Deploy : BuildType({
    DeploymentBuilders.createGithubReleaseDeployment(
        assetsPath = "_deploy/*"
    )(this)
    
    vcs {
        root(Fernflower.vcsRoots.Fernflower_GitGithubComK88936fernflowerGitRefsHeadsMaster)
    }
    
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
    params{
        password("env.GITHUB_TOKEN", "credentialsJSON:04d96fb0-dbf8-457b-be29-2327ab11dd68")
    }
})