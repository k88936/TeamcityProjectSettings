package WebstormProjects.ReactNative.Fcalender

import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    name = "Fcalender"
    subProject(WebstormProjects.ReactNative.Fcalender.frontend.Project)
})
