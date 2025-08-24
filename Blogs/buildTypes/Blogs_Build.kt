package Blogs.buildTypes

import _Self.utils.DeploymentBuilders
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.nodeJS
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Blogs_Build : BuildType({
    id("Blogs_Build")
    name = "Build"

    vcs {
        root(Blogs.vcsRoots.Blogs_GitGithubComK88936blogsGitRefsHeadsMain)
    }

    steps {
        nodeJS {
            id = "nodejs_runner"
            shellScript = """
                npm install
                npm run build
                """.trimIndent()
        }
        DeploymentBuilders.createGitPushStep("build pages")(this)
    }

    triggers {
        vcs {
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