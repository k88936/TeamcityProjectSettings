package WebstormProjects.ReactNative.Fcalender

import WebstormProjects.ReactNative.Fcalender.buildTypes.Fcalender_Build
import WebstormProjects.ReactNative.Fcalender.buildTypes.Fcalender_Deploy
import WebstormProjects.ReactNative.Fcalender.vcsRoots.FcalenderMain
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    name = "Fcalender"

    buildType(Fcalender_Build)
    buildType(Fcalender_Deploy)
    vcsRoot(FcalenderMain)
})
