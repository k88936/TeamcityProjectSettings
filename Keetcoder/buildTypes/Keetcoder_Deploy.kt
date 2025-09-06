package Keetcoder.buildTypes

import Keetcoder.vcsRoots.Keetcoder_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import utils.DeploymentBuilders

object Keetcoder_Deploy : BuildType({
    ->
    DeploymentBuilders.createGithubReleaseDeployment(
        vcsRoot = Keetcoder_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster,
        assetsPath = "_deploy/*"
    )(this)

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