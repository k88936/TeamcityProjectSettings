package GithubPage.buildTypes

import _Self.utils.DeploymentBuilders
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.script

object GithubPage_Build : BuildType({
    id("GitGithubComK88936k88936githubIoGit_Build")
    name = "Build"

    vcs {
        root(GithubPage.vcsRoots.GithubPage_GitGithubComK88936k88936githubIoGit)
    }

    steps {
        script {
            id = "simpleRunner"
            scriptContent = "sh build.sh"
        }

        DeploymentBuilders.createGitPushStep()
    }

    requirements {
        exists("env.PLATFORM_LINUX")
    }
})