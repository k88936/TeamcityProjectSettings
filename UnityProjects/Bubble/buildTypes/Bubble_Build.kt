package UnityProjects.Bubble.buildTypes

import UnityProjects.Bubble.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.*
import utils.unity.UnityBuildTarget
import utils.unity.applyUnityDefaults

object Bubble_Build : BuildType({
    name = "Build"

    vcs {
        root(Bubble_GitGithubComK88936BubbleGitRefsHeadsMain)
    }

    applyUnityDefaults(
        executeMethod = "DemoGame.Editor.BuildScript.PerformBuild_Windows",
        buildTarget = UnityBuildTarget.StandaloneWindows64,
        buildPath = "Build\\Windows"
    )
})
