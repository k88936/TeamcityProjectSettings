package IdeaProjects.Keetcoder.buildTypes

import IdeaProjects.Keetcoder.vcsRoots.Keetcoder_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster
import Utils.GithubReleaseDeployTemplate.createGithubReleaseDeployment
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger

object Keetcoder_Deploy : BuildType({
    createGithubReleaseDeployment(
        vcsRoot = Keetcoder_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster,
        assetsPath = "_deploy/*"
    )(this)

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