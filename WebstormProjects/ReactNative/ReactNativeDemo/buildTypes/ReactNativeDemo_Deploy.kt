package WebstormProjects.ReactNative.ReactNativeDemo.buildTypes

import Utils.Deploy.applyGithubReleaseDeployment
import Utils.Deploy.applySourceOfDeployment
import WebstormProjects.ReactNative.ReactNativeDemo.vcsRoots.ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger

object ReactNativeDemo_Deploy : BuildType({
    name = "Deploy"
    type = Type.DEPLOYMENT

    triggers {
        finishBuildTrigger {
            buildType = ReactNativeDemo_Build.id?.value
            successfulOnly = true
        }
    }

    vcs {
        root(ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain)
    }
    dependencies {
        artifacts(ReactNativeDemo_Build) {
            buildRule = lastSuccessful()
            artifactRules = "*=>_deploy/"
        }
    }

    applySourceOfDeployment(
        name = "react-native-demo",
        assets = "_deploy/*"
    )
    applySourceOfDeployment(
        name = "react-native-demo",
        tagPattern = "latest",
        assets = "_deploy/*"
    )
    applyGithubReleaseDeployment(
        assetsPath = "_deploy/*",
    )


})
