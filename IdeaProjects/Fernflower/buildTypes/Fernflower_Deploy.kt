package IdeaProjects.Fernflower.buildTypes

import Utils.Deploy.applyGithubReleaseDeployment
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger

object Fernflower_Deploy : BuildType({
    name = "Deploy"
    id("Fernflower_Deploy")
    type = Type.DEPLOYMENT

    vcs {
        root(IdeaProjects.Fernflower.vcsRoots.Fernflower_GitGithubComK88936fernflowerGitRefsHeadsMaster)
    }
    applyGithubReleaseDeployment(
        assetsPath = "_deploy/*"
    )


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