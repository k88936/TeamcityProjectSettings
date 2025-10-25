package WebstormProjects.ReactNative.ReactNativeDemo.buildTypes

import Utils.GithubReleaseDeployTemplate.createGithubReleaseDeployment
import Utils.LarkDriveDeployTemplate
import Utils.SourceOfDeployTemplate
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

    params {
        password("env.FEISHU_ACCESS_TOKEN", "credentialsJSON:27470558-ab85-4fba-b4ff-10f7f5a54767")
    }

    SourceOfDeployTemplate.createSourceOfDeployment(
        name = "react-native-demo",
        tagPattern = "v%build.number",
        assets = "_deploy/*"
    )(this)
    createGithubReleaseDeployment(
        vcsRoot = ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain,
        assetsPath = "_deploy/*",
    )(this)



})
