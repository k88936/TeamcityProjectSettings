package RustroverProjects.Source.buildTypes

import RustroverProjects.Source.vcsRoots.Source_GitGithubComK88936sourceGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import utils.tauri.applyTauriInitStep
import utils.tauri.applyTauriLinuxBuildStep
import utils.tauri.genTauriLinuxArtifactRules

object Source_Build_Linux : BuildType({
    id("Source_Build_Linux")
    name = "Build Linux"

    vcs {
        root(Source_GitGithubComK88936sourceGitRefsHeadsMain)
    }

    artifactRules = genTauriLinuxArtifactRules("source")

    applyTauriInitStep()
    applyTauriLinuxBuildStep()

    features {
        perfmon { }
    }
})
