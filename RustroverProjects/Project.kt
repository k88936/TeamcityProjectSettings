package RustroverProjects

import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("RustroverProjects")
    name = "Rustrover Projects"
    subProject(RustroverProjects.OllamaProxy.Project)
    subProject(RustroverProjects.TunetRust.Project)
    subProject(RustroverProjects.Gold.Project)
    subProject(RustroverProjects.Rustdesk.Project)
})