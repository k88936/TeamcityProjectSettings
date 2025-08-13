package LeetcodeEditor.buildTypes

import LeetcodeEditor.vcsRoots.LeetcodeEditor_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import _Self.utils.DeploymentBuilders

object LeetcodeEditor_Deploy : BuildType({
    ->
    DeploymentBuilders.createGithubReleaseDeployment(
        assetsPath = "_deploy/*"
    )(this)

    vcs {
        root(LeetcodeEditor_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster)
    }

    triggers {
        finishBuildTrigger {
            buildType = "${LeetcodeEditor_Build.id}"
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(LeetcodeEditor_Build) {
            buildRule = lastSuccessful()
            cleanDestination = true
            artifactRules = "*=>_deploy/"
        }
    }
})