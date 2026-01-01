package IdeaProjects.Keetcoder.buildTypes

import IdeaProjects.Keetcoder.vcsRoots.Keetcoder_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import utils.deploy.applyGithubReleaseDeployment

object Keetcoder_Deploy : BuildType({
    id("Keetcoder_Deploy")
    name = "deploy"
    type = Type.DEPLOYMENT
    vcs {
        root(Keetcoder_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster)
    }
    applyGithubReleaseDeployment(
        assetsPath = "_deploy/*"
    )

    triggers {
        finishBuildTrigger {
            buildType = Keetcoder_Build.id?.value
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(Keetcoder_Build) {
            buildRule = lastSuccessful()
            cleanDestination = true
            artifactRules = "*=>_deploy/"
        }
    }
})