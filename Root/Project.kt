package Root

import jetbrains.buildServer.configs.kotlin.ParameterDisplay
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    description = "Contains all other projects"

    features {
    }

    cleanup {
        baseRule {
            preventDependencyCleanup = false
        }
    }

    subProject(IdeaProjects.Project)
    subProject(ClionProjects.Project)
    subProject(UnityProjects.Project)
    subProject(DockerProjects.Project)
    subProject(WebstormProjects.Project)
    subProject(RustroverProjects.Project)

})