package RustroverProjects.TunetRust.buildTypes

import RustroverProjects.TunetRust.vcsRoots.TunetRust_GitGithubComK88936tunetRustGitRefsHeadsMaster
import Utils.Deploy.applyGithubReleaseDeployment
import Utils.Deploy.applySourceOfDeployment
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger


object TunetRust_Deploy : BuildType({
    id("TunetRust_Deploy")
    name = "Deploy"
    type = Type.DEPLOYMENT

    triggers {
        finishBuildTrigger {
            buildType = TunetRust_Build.id?.value
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(TunetRust_Build) {
            buildRule = lastSuccessful()
            artifactRules = "*=>_deploy/"
        }
    }

    vcs {
        root(TunetRust_GitGithubComK88936tunetRustGitRefsHeadsMaster)
    }
    applySourceOfDeployment("tunet-rust")
    applyGithubReleaseDeployment(
        assetsPath = "_deploy/*"
    )


})
