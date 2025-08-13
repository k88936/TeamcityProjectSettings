package Bubble.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.failureConditions.BuildFailureOnText
import jetbrains.buildServer.configs.kotlin.failureConditions.failOnText
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Bubble_Build : BuildType({
    name = "Build"

    artifactRules = """
        Build\Windows => Build\Windows
        -:Build\Windows\Bubble_BurstDebugInformation_DoNotShip
        -:Build\Windows\DemoGame_BackUpThisFolder_ButDontShipItWithYourGame
    """.trimIndent()
    publishArtifacts = PublishMode.SUCCESSFUL

    vcs {
        root(Bubble.vcsRoots.Bubble_GitGithubComK88936BubbleGitRefsHeadsMain)
    }

    steps {
        step {
            id = "build"
            type = "unity"
            param("executeMethod", "DemoGame.Editor.BuildScript.PerformBuild_Windows")
            param("noGraphics", "true")
            param("buildTarget", "StandaloneWindows64")
        }
    }

    triggers {
        vcs {
        }
    }

    failureConditions {
        testFailure = false
        nonZeroExitCode = false
        javaCrash = false
        failOnText {
            conditionType = BuildFailureOnText.ConditionType.CONTAINS
            pattern = "Build Finished, Result: Failure."
            failureMessage = "Unity Build Failure."
            reverse = false
            stopBuildOnFailure = true
        }
    }

    features {
        perfmon {
        }
    }

    requirements {
        exists("env.UNITY")
        exists("env.PLATFORM_WIN")
    }
})
