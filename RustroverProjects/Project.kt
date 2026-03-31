package RustroverProjects

import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("RustroverProjects")
    name = "Rustrover Projects"
    subProject(RustroverProjects.Citizen.Project)
})