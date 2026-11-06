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
            buildType = Source_Build.id?.value
            successfulOnly = true
        }
    }

    dependencies {
        snapshot(Source_Build_Linux) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
        snapshot(Source_Build_Windows) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
        snapshot(Source_Build_Android) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }

        artifacts(Source_Build_Linux) {
            buildRule = sameChainOrLastFinished()
            artifactRules = """
                +:target/release/source=>_deploy/
                +:target/release/bundle/deb/*.deb=>_deploy/
                +:target/release/bundle/rpm/*.rpm=>_deploy/
                +:target/release/bundle/appimage/*.AppImage=>_deploy/
            """.trimIndent()
        }
        artifacts(Source_Build_Windows) {
            buildRule = sameChainOrLastFinished()
            artifactRules = """
                +:target/x86_64-pc-windows-msvc/release/source.exe=>_deploy/
                +:target/x86_64-pc-windows-msvc/release/bundle/nsis/*-setup.exe=>_deploy/
            """.trimIndent()
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
