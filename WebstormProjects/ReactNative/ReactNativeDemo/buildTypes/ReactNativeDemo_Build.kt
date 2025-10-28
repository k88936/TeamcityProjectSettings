package WebstormProjects.ReactNative.ReactNativeDemo.buildTypes

import Utils.AI.ContinueAITemplate
import WebstormProjects.ReactNative.ReactNativeBuildTemplate
import WebstormProjects.ReactNative.ReactNativeDemo.vcsRoots.ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object ReactNativeDemo_Build : BuildType({
    name = "Build"

    artifactRules = "android/app/build/outputs/apk/release/app-release.apk"

    params {
        param("env.NODE_OPTIONS", "--max_old_space_size=4096")
    }

    vcs {
        root(ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain)
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }

    ContinueAITemplate.createStep("say hello")(this)
    ReactNativeBuildTemplate.createReactNativeAndroidBuild()(this)
})
