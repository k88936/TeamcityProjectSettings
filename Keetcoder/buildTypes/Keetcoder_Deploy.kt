package Keetcoder.buildTypes

import Keetcoder.vcsRoots.Keetcoder_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import _Self.utils.DeploymentBuilders

object Keetcoder_Deploy : BuildType({
    ->
    DeploymentBuilders.createGithubReleaseDeployment(
        assetsPath = "_deploy/*"
    )(this)

    vcs {
        root(Keetcoder_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster)
    }

    triggers {
        finishBuildTrigger {
            buildType = "${Keetcoder_Build.id}"
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