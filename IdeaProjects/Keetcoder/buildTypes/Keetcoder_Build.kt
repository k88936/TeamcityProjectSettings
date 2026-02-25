package IdeaProjects.Keetcoder.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.PublishMode
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Keetcoder_Build : BuildType({
    name = "Build"

    artifactRules = "build/distributions"
    publishArtifacts = PublishMode.SUCCESSFUL

    vcs {
        root(IdeaProjects.Keetcoder.vcsRoots.Keetcoder_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster)
    }

    steps {
        gradle {
            id = "gradle_runner"
            tasks = "buildPlugin"
            dockerImage = "kvtodev/ci-containers:java"
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
