package RssReader.buildTypes

import _Self.utils.DeploymentBuilders
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.nodeJS
import jetbrains.buildServer.configs.kotlin.triggers.schedule
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object RssReader_Build : BuildType({
    id("RssReader_Build")
    name = "Build"

    vcs {
        root(RssReader.vcsRoots.RssReader_GitGithubComK88936blogsGitRefsHeadsMain)
    }

    steps {
        nodeJS {
            id = "nodejs_runner"
            shellScript = """
                npm install
                npm run build
                npm run fetch-feeds
                """.trimIndent()
        }
        DeploymentBuilders.createGitPushStep("build pages")(this)
    }

    triggers {
        vcs {
        }
        schedule {
            triggerBuild = always()
            withPendingChangesOnly = false
        }
    }

    features {
        perfmon {
        }
    }

    requirements {
        exists("env.JS")
    }
})