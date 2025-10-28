package WebstormProjects.ReactNative.Fcalender.frontend

import WebstormProjects.ReactNative.Fcalender.frontend.buildTypes.FcalenderFrontendBuild
import WebstormProjects.ReactNative.Fcalender.frontend.buildTypes.FcalenderFrontendDeploy
import WebstormProjects.ReactNative.Fcalender.vcsRoots.FcalenderMain
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    name = "Fcalender Frontend"

    buildType(FcalenderFrontendBuild)
    buildType(FcalenderFrontendDeploy)
    vcsRoot(FcalenderMain)
})
