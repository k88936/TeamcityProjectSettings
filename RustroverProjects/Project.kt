package RustroverProjects

import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("RustroverProjects")
    name = "Rustrover Projects"
    subProject(RustroverProjects.Talkful.Project)
})
