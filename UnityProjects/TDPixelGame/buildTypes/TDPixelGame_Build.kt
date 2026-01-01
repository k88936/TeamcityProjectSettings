package UnityProjects.TDPixelGame.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object TDPixelGame_Build : BuildType({
    name = "Build"

    artifactRules = """
        Build\Windows\=>\
        -:Build\Windows\DemoGame_BackUpThisFolder_ButDontShipItWithYourGame
        -:Build\Windows\world simulator_BurstDebugInformation_DoNotShip
    """.trimIndent()

    vcs {
        root(UnityProjects.TDPixelGame.vcsRoots.TDPixelGame_GitGithubCom20220120802dPixelGameGitRefsHeadsMain)
    }

    steps {
        step {
            id = "unity"
            type = "unity"
            param("executeMethod", "Editor.BuildScript.PerformBuild_Windows")
            param("unityVersion", "6000.0.49")
            param("silentCrashes", "true")
            param("noGraphics", "true")
            param("buildTarget", "StandaloneWindows64")
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