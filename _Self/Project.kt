package _Self

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.buildReportTab

object Project : Project({
    description = "Contains all other projects"

    features {
        buildReportTab {
            id = "PROJECT_EXT_1"
            title = "Code Coverage"
            startPage = "coverage.zip!index.html"
        }
    }

    cleanup {
        baseRule {
            preventDependencyCleanup = false
        }
    }

    subProject(Gold.Project)
    subProject(SourceOf.Project)
    subProject(UnityProjects.Project)
    subProject(Fernflower.Project)
    subProject(LeetcodeEditor.Project)
    subProject(Shotmd.Project)
})
