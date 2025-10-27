package WebstormProjects.ReactNative.Fcalender

import WebstormProjects.ReactNative.ReactNativeDemo.buildTypes.ReactNativeDemo_Build
import WebstormProjects.ReactNative.ReactNativeDemo.buildTypes.ReactNativeDemo_Deploy
import WebstormProjects.ReactNative.ReactNativeDemo.vcsRoots.ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    name = "Fcalender"

    buildType(ReactNativeDemo_Build)
    buildType(ReactNativeDemo_Deploy)
    vcsRoot(ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain)
})
