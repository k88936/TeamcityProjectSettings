package IdeaProjects.Fernflower.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import utils.build_steps.getDockerRunParameters

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
            dockerImage = "kvtodev/ci-containers:java"
            dockerRunParameters = getDockerRunParameters()
            dockerPull = true
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
