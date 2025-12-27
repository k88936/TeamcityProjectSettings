package WebstormProjects.ReactNative.ReactNativeDemo.buildTypes

import Utils.Trigger.excludeCI
import WebstormProjects.ReactNative.ReactNativeBuildTemplate
import WebstormProjects.ReactNative.ReactNativeDemo.vcsRoots.ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon

object ReactNativeDemo_Build : BuildType({
    name = "Build"

    artifactRules = "android/app/build/outputs/apk/release/app-release.apk"

    vcs {
        root(ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain)
    }


    excludeCI()
    features {
        perfmon {
        }
    }

    ReactNativeBuildTemplate.createReactNativeAndroidBuild()(this)
})
