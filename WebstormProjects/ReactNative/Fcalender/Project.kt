package WebstormProjects.ReactNative.Fcalender

import jdk.internal.util.StaticProperty.userName
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.dockerRegistry

object Project : Project({
    name = "Fcalender"
    id("Fcalender")
    subProject(WebstormProjects.ReactNative.Fcalender.frontend.Project)
})
