package RustroverProjects.Source.buildTypes

import RustroverProjects.Source.vcsRoots.Source_GitGithubComK88936sourceGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.FailureAction
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import utils.deploy.applyGithubReleaseDeployment
import utils.deploy.applySourceOfDeployment

object Source_Deploy : BuildType({
    id("Source_Deploy")
    name = "deploy"
    type = Type.DEPLOYMENT

    vcs {
        root(Source_GitGithubComK88936sourceGitRefsHeadsMain)
        cleanCheckout = true
    }

    triggers {
        finishBuildTrigger {
            buildType = Source_Build_Android.id?.value
            successfulOnly = true
        }
    }

    dependencies {
        snapshot(Source_Build_Android) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }

        artifacts(Source_Build_Android) {
            buildRule = sameChainOrLastFinished()
            artifactRules = """
                +:src-tauri/gen/android/app/build/outputs/apk/universal/release/*.apk=>_deploy/
            """.trimIndent()
        }
    }

    applySourceOfDeployment("source")
    applyGithubReleaseDeployment(
        assetsPath = "_deploy/*"
    )

    features {
        perfmon { }
    }
})
