package WebstormProjects.ReactNative.ReactNativeDemo

import WebstormProjects.ReactNative.ReactNativeDemo.buildTypes.ReactNativeDemo_Build
import WebstormProjects.ReactNative.ReactNativeDemo.buildTypes.ReactNativeDemo_Deploy
import WebstormProjects.ReactNative.ReactNativeDemo.buildTypes.ReactNativeDemo_i18Check
import WebstormProjects.ReactNative.ReactNativeDemo.vcsRoots.ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("WebstormProjects_ReactNativeDemo")
    name = "React Native Demo"

    buildType(ReactNativeDemo_i18Check)
    buildType(ReactNativeDemo_Build)
    buildType(ReactNativeDemo_Deploy)
    vcsRoot(ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain)
})
