package WebstormProjects.ReactNative.Fcalender.frontend

import WebstormProjects.ReactNative.Fcalender.frontend.buildTypes.FcalenderFrontendBuild
import WebstormProjects.ReactNative.Fcalender.frontend.buildTypes.FcalenderFrontendTest
import WebstormProjects.ReactNative.Fcalender.frontend.vcsRoots.FcalenderFrontendVCS
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    name = "Fcalender Frontend"
    id("FcalenderFrontend")

    buildType(FcalenderFrontendTest)
    buildType(FcalenderFrontendBuild)
//    buildType(FcalenderFrontendApiCheck)
//    buildType(FcalenderFrontendi18nCheck)
    vcsRoot(FcalenderFrontendVCS)
})
