package IdeaProjects.Fernflower.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import Utils.GithubReleaseDeployTemplate.createGithubReleaseDeployment

object Fernflower_Deploy : BuildType({
    createGithubReleaseDeployment(
        vcsRoot = IdeaProjects.Fernflower.vcsRoots.Fernflower_GitGithubComK88936fernflowerGitRefsHeadsMaster,
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