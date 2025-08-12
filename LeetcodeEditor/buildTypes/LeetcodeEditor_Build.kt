package LeetcodeEditor.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object LeetcodeEditor_Build : BuildType({
    name = "Build"

    artifactRules = "build/distributions"
    publishArtifacts = PublishMode.SUCCESSFUL

    vcs {
        root(LeetcodeEditor.vcsRoots.LeetcodeEditor_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster)
    }

    steps {
        gradle {
            id = "gradle_runner"
            tasks = "clean buildPlugin"
            jdkHome = "%env.JDK_17_0%"
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
