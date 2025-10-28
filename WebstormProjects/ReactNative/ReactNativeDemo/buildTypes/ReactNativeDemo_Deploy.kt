package WebstormProjects.ReactNative.ReactNativeDemo.buildTypes

import Utils.Deploy.GithubReleaseDeployTemplate.createGithubReleaseDeployment
import Utils.Deploy.SourceOfDeployTemplate
import WebstormProjects.ReactNative.ReactNativeDemo.vcsRoots.ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger

object ReactNativeDemo_Deploy : BuildType({
    name = "Deploy"
    type= Type.DEPLOYMENT

    triggers {
        finishBuildTrigger {
            buildType = ReactNativeDemo_Build.id?.value
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(ReactNativeDemo_Build) {
            buildRule = lastSuccessful()
            artifactRules = "*=>_deploy/"
        }
    }

    SourceOfDeployTemplate.createSourceOfDeployment(
        name = "react-native-demo",
        assets = "_deploy/*"
    )(this)
    SourceOfDeployTemplate.createSourceOfDeployment(
        name = "react-native-demo",
        tagPattern = "latest",
        assets = "_deploy/*"
    )(this)
    createGithubReleaseDeployment(
        vcsRoot = ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain,
        assetsPath = "_deploy/*",
    )(this)



})
