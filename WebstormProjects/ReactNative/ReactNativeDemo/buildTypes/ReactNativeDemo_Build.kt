package WebstormProjects.ReactNative.ReactNativeDemo.buildTypes

import WebstormProjects.ReactNative.ReactNativeBuildTemplate
import WebstormProjects.ReactNative.ReactNativeDemo.vcsRoots.ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object ReactNativeDemo_Build : BuildType({
    id("WebstormProjects_ReactNativeDemo_Build")
    name = "Build"

    vcs {
        root(ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain)
    }

    steps {
        ReactNativeBuildTemplate.createReactNativeAndroidBuild()(this)
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})
