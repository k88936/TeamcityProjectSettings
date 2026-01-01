package UnityProjects

import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("UnityProjects")
    name = "UnityProjects"
    subProject(UnityProjects.Bubble.Project)
    subProject(UnityProjects.TDPixelGame.Project)
})