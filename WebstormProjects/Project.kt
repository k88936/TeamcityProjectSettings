package WebstormProjects

import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("WebstormProjects")
    name = "Webstorm Projects"
    subProject(WebstormProjects.GithubPages.Projects)
})