package ClionProjects

import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("ClionProjects")
    name = "Clion Projects"
    subProject(ClionProjects.Qt.Shotmd.Project)
})