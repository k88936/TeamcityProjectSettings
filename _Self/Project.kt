package _Self

import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.buildReportTab

object Project : Project({
    description = "Contains all other projects"

    features {
    }

    cleanup {
        baseRule {
            preventDependencyCleanup = false
        }
    }

    subProject(Gold.Project)
    subProject(Fernflower.Project)
    subProject(Keetcoder.Project)
    subProject(Shotmd.Project)
    subProject(Bubble.Project)
    subProject(Nextcloud.Project)
    subProject(Sites.Projects)
    subProject(TeamcityAgent.Project)
    subProject(OllamaProxy.Project)
})