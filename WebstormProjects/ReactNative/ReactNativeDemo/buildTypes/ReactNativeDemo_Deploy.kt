package WebstormProjects.ReactNative.ReactNativeDemo.buildTypes

import ClionProjects.Qt.Shotmd.vcsRoots.Shotmd_GitGithubComK88936shotmdGitRefsHeadsMaster
import Utils.GithubReleaseDeployTemplate.createGithubReleaseDeployment
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script

object ReactNativeDemo_Deploy : BuildType({
    name = "Deploy"

    var root =
        WebstormProjects.ReactNative.ReactNativeDemo.vcsRoots.ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain
    vcs {
        root(root)
    }

    dependencies {
        artifacts(ReactNativeDemo_Build) {
            buildRule = lastSuccessful()
            artifactRules = "*=>_deploy/"
        }
    }

    createGithubReleaseDeployment(
        vcsRoot = root,
        assetsPath = "_deploy/*",
    )(this)
})
