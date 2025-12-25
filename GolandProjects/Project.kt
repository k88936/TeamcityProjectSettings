package GolandProjects

import jetbrains.buildServer.configs.kotlin.Project
import GolandProjects.LocalRouter.Project as LocalRouterProject

object Project : Project({
    id("GolandProjects")
    name = "Goland Projects"

    subProject(LocalRouterProject)
})