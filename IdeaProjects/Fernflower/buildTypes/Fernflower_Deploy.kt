package IdeaProjects.Fernflower.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import Utils.Deploy.GithubReleaseDeployTemplate.createGithubReleaseDeployment

object Fernflower_Deploy : BuildType({
    name = "Deploy"
    id("Fernflower_Deploy")
    type = Type.DEPLOYMENT

    createGithubReleaseDeployment(
        vcsRoot = IdeaProjects.Fernflower.vcsRoots.Fernflower_GitGithubComK88936fernflowerGitRefsHeadsMaster,
        assetsPath = "_deploy/*"
    )(this)


    triggers {
        finishBuildTrigger {
            buildType = Fernflower_Build.id?.value
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