package RustroverProjects.Gold.buildTypes

import RustroverProjects.Gold.vcsRoots.Gold_GitGithubComK88936goldGitRefsHeadsMain
import Utils.Deploy.GithubReleaseDeployTemplate
import Utils.Deploy.SourceOfDeployTemplate
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger

object Gold_Deploy : BuildType({
    id("Gold_Deploy")
    name = "Deploy"
    type = Type.DEPLOYMENT

    vcs {
        root(Gold_GitGithubComK88936goldGitRefsHeadsMain)
    }

    triggers {
        finishBuildTrigger {
            buildType = Gold_Build.id?.value
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(Gold_Build) {
            buildRule = lastSuccessful()
            artifactRules = "*=>_deploy/"
        }
    }

    SourceOfDeployTemplate.createSourceOfDeployment(
        name = "gold",
        tagPattern = "v1.0.0",
        assets = "_deploy/gold"
    )(this)
    GithubReleaseDeployTemplate.createGithubReleaseDeployment(
        assetsPath = "_deploy/gold",
    )(this)


    features {
        perfmon { }
    }
})
