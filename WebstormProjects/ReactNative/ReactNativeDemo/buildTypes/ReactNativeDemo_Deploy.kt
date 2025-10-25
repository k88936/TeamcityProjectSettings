package WebstormProjects.ReactNative.ReactNativeDemo.buildTypes

import Utils.GithubReleaseDeployTemplate.createGithubReleaseDeployment
import Utils.LarkDriveDeployTemplate
import WebstormProjects.ReactNative.ReactNativeDemo.vcsRoots.ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger

object ReactNativeDemo_Deploy : BuildType({
    name = "Deploy"

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

    createGithubReleaseDeployment(
        vcsRoot = ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain,
        assetsPath = "_deploy/*",
    )(this)

    LarkDriveDeployTemplate.createLarkDriveDeployment(
        file = "_deploy/app-release.apk",
        parentNode = "EgwcfU39olEhT1dtX3fcWRF0nig",
        rename = "build-%build.number%.apk"
    )(this)


})
