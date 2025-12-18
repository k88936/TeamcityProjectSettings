package WebstormProjects.ReactNative.Fcalender

import WebstormProjects.ReactNative.Fcalender.buildTypes.FcalenderBackendBuild
import WebstormProjects.ReactNative.Fcalender.buildTypes.FcalenderFrontendBuild
import WebstormProjects.ReactNative.Fcalender.vcsRoots.FcalenderVCS
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    name = "Fcalender"
    id("Fcalender")

    buildType(FcalenderBackendBuild)
    buildType(FcalenderFrontendBuild)
//    buildType(FcalenderFrontendApiCheck)
//    buildType(FcalenderFrontendi18nCheck)
    vcsRoot(FcalenderVCS)
})