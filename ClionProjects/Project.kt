package ClionProjects

import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("ClionProjects")
    name = "Qt Projects"
    subProject(ClionProjects.Qt.Shotmd.Project)
})