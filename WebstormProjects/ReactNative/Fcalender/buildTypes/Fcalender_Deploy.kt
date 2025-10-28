package WebstormProjects.ReactNative.Fcalender.buildTypes

import Utils.GithubReleaseDeployTemplate.createGithubReleaseDeployment
import Utils.SourceOfDeployTemplate
import WebstormProjects.ReactNative.Fcalender.vcsRoots.FcalenderMain
import WebstormProjects.ReactNative.ReactNativeDemo.vcsRoots.ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger

object Fcalender_Deploy : BuildType({
    name = "Deploy"
    type = Type.DEPLOYMENT

    triggers {
        finishBuildTrigger {
            buildType = Fcalender_Build.id?.value
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(Fcalender_Build) {
            buildRule = lastSuccessful()
            artifactRules = "*=>_deploy/"
        }
    }

    SourceOfDeployTemplate.createSourceOfDeployment(
        name = "Fcalender",
        assets = "_deploy/*"
    )(this)
    SourceOfDeployTemplate.createSourceOfDeployment(
        name = "Fcalender",
        tagPattern = "latest",
        assets = "_deploy/*"
    )(this)
    createGithubReleaseDeployment(
        tagPattern = "build-%build.number%",
        vcsRoot = FcalenderMain,
        assetsPath = "_deploy/*",
    )(this)


})
