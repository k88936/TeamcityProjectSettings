package utils.unity

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.PublishMode
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.failureConditions.BuildFailureOnText
import jetbrains.buildServer.configs.kotlin.failureConditions.failOnText
import jetbrains.buildServer.configs.kotlin.triggers.vcs

enum class UnityBuildTarget(val value: String) {
    StandaloneWindows64("StandaloneWindows64"),
    StandaloneOSX("StandaloneOSX"),
    StandaloneLinux64("StandaloneLinux64"),
    Android("Android"),
    iOS("iOS")
}

fun BuildType.applyUnityBuildSteps(
    executeMethod: String,
    buildTarget: UnityBuildTarget = UnityBuildTarget.StandaloneWindows64,
    noGraphics: Boolean = true,
    stepId: String = "build"
) {
    steps {
        step {
            id = stepId
            type = "unity"
            param("executeMethod", executeMethod)
            param("noGraphics", noGraphics.toString())
            param("buildTarget", buildTarget.value)
        }
    }
}

fun BuildType.applyUnityFailureConditions() {
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
}

fun BuildType.applyUnityArtifacts(
    buildPath: String = "Build/Windows",
    excludeDebugFolders: Boolean = true
) {
    val excludes = if (excludeDebugFolders) {
        val basePath = buildPath.replace("/", "\\")
        """
            -:$basePath\\*_BurstDebugInformation_DoNotShip
            -:$basePath\\*_BackUpThisFolder_ButDontShipItWithYourGame
        """.trimIndent()
    } else ""

    artifactRules = "$buildPath => $buildPath\n$excludes".trimIndent()
    publishArtifacts = PublishMode.SUCCESSFUL
}

fun BuildType.applyUnityRequirements(
    requireUnity: Boolean = true,
    platformEnv: String? = "PLATFORM_WIN"
) {
    requirements {
        if (requireUnity) {
            exists("env.UNITY")
        }
        platformEnv?.let { exists("env.$it") }
    }
}

fun BuildType.applyUnityDefaults(
    executeMethod: String,
    buildTarget: UnityBuildTarget = UnityBuildTarget.StandaloneWindows64,
    buildPath: String = "Build/Windows",
    platformEnv: String? = "PLATFORM_WIN"
) {
    applyUnityBuildSteps(executeMethod, buildTarget)
    applyUnityFailureConditions()
    applyUnityArtifacts(buildPath)
    applyUnityRequirements(platformEnv = platformEnv)
    features {
        perfmon {}
    }
    triggers {
        vcs {}
    }
}
