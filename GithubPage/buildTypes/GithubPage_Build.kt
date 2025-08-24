package GithubPage.buildTypes

import _Self.utils.DeploymentBuilders
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.nodeJS
import jetbrains.buildServer.configs.kotlin.buildSteps.script

object GithubPage_Build : BuildType({
    id("GithubPage_Build")
    name = "Build"

    vcs {
        root(GithubPage.vcsRoots.GithubPage_GitGithubComK88936k88936githubIoGit)
    }

    steps {
        nodeJS {
            id = "nodejs_runner"
            shellScript = """
                npm install
                npm run build
            """.trimIndent()
        }
        DeploymentBuilders.createGitPushStep("build")(this)
    }

    requirements {
        exists("env.PLATFORM_LINUX")
    }
})