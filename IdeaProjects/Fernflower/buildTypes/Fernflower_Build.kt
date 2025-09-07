package IdeaProjects.Fernflower.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Fernflower_Build : BuildType({
    name = "Build"

    artifactRules = "build/libs/"

    vcs {
        root(IdeaProjects.Fernflower.vcsRoots.Fernflower_GitGithubComK88936fernflowerGitRefsHeadsMaster)
    }

    steps {
        gradle {
            id = "gradle_runner"
            tasks = "clean build"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})
