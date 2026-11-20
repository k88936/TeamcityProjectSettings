package RustroverProjects.Talkful.buildTypes

import RustroverProjects.Talkful.vcsRoots.Talkful_GitGithubComK88936talkfulGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import utils.deploy.applyGithubReleaseDeployment

object Talkful_Deploy : BuildType({
    id("Talkful_Deploy")
    name = "deploy"
    type = Type.DEPLOYMENT

    vcs {
        root(Talkful_GitGithubComK88936talkfulGitRefsHeadsMain)
    }

    triggers {
        finishBuildTrigger {
            buildType = Talkful_Build.id?.value
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(Talkful_Build) {
            buildRule = lastSuccessful()
            artifactRules = "*=>_deploy/"
        }
    }
    applyGithubReleaseDeployment(
        assetsPath = "_deploy/*"
    )
    features {
        perfmon { }
    }
})

