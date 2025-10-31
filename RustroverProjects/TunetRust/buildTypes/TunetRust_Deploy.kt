package RustroverProjects.TunetRust.buildTypes

import RustroverProjects.TunetRust.vcsRoots.TunetRust_GitGithubComK88936tunetRustGitRefsHeadsMaster
import Utils.Deploy.GithubReleaseDeployTemplate.createGithubReleaseDeployment
import Utils.Deploy.SourceOfDeployTemplate
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
    SourceOfDeployTemplate.createSourceOfDeployment("tunet-rust")(this)
    createGithubReleaseDeployment(
        assetsPath = "_deploy/*"
    )(this)


})
