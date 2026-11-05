package RustroverProjects.Source.buildTypes

import RustroverProjects.Source.vcsRoots.Source_GitGithubComK88936sourceGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
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
    }

    triggers {
        finishBuildTrigger {
            buildType = Source_Build.id?.value
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(Source_Build) {
            buildRule = sameChainOrLastFinished()
            cleanDestination = true
            artifactRules = """
                +:target/release/source=>_deploy/
                +:target/release/bundle/deb/*.deb=>_deploy/
                +:target/release/bundle/rpm/*.rpm=>_deploy/
                +:target/release/bundle/appimage/*.AppImage=>_deploy/
                +:target/x86_64-pc-windows-msvc/release/source.exe=>_deploy/
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
