package WebstormProjects.ReactNative.Fcalender

import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    name = "Fcalender"
    id("Fcalender")
    subProject(WebstormProjects.ReactNative.Fcalender.frontend.Project)
    subProject(WebstormProjects.ReactNative.Fcalender.backend.Project)
})
