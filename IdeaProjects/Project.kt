package IdeaProjects

import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id ("IdeaProjects")
    name = "Idea Projects"
    subProject(IdeaProjects.Keetcoder.Project)
    subProject(IdeaProjects.Fernflower.Project)


})