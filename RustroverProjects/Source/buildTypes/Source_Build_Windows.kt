package RustroverProjects.Source.buildTypes

import RustroverProjects.Source.vcsRoots.Source_GitGithubComK88936sourceGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import utils.tauri.applyTauriInitStep
import utils.tauri.applyTauriWindowsBuildStep
import utils.tauri.genTauriWindowsArtifactRules

object Source_Build_Windows : BuildType({
    id("Source_Build_Windows")
    name = "Build Windows"

    vcs {
        root(Source_GitGithubComK88936sourceGitRefsHeadsMain)
    }

    artifactRules = genTauriWindowsArtifactRules("source")

    applyTauriInitStep()
    applyTauriWindowsBuildStep()

    features {
        perfmon { }
    }
})
