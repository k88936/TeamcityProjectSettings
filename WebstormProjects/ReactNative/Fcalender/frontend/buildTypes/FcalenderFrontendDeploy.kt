package WebstormProjects.ReactNative.Fcalender.frontend.buildTypes

import Utils.GithubReleaseDeployTemplate.createGithubReleaseDeployment
import Utils.SourceOfDeployTemplate
import WebstormProjects.ReactNative.Fcalender.vcsRoots.FcalenderMain
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger

object FcalenderFrontendDeploy : BuildType({
    name = "Deploy"
    type = Type.DEPLOYMENT

    triggers {
        finishBuildTrigger {
            buildType = FcalenderFrontendBuild.id?.value
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(FcalenderFrontendBuild) {
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
