package _Self

import Blogs.buildTypes.Blogs_Build
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
    subProject(Fernflower.Project)
    subProject(Keetcoder.Project)
    subProject(Shotmd.Project)
    subProject(Bubble.Project)
    subProject(GithubPage.Project)
    subProject(Nextcloud.Project)
    subProject(Blogs.Project)
})