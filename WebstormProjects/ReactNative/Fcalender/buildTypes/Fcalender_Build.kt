package WebstormProjects.ReactNative.Fcalender.buildTypes

import WebstormProjects.ReactNative.Fcalender.vcsRoots.FcalenderMain
import WebstormProjects.ReactNative.ReactNativeBuildTemplate
import WebstormProjects.ReactNative.ReactNativeDemo.vcsRoots.ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Fcalender_Build : BuildType({
    name = "Build"

    artifactRules = "frontend/android/app/build/outputs/apk/release/app-release.apk"

    params {
        param("env.NODE_OPTIONS", "--max_old_space_size=4096")
    }

    vcs {
        root(FcalenderMain)
    }


    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }

    ReactNativeBuildTemplate.createReactNativeAndroidBuild("frontend")(this)
})
