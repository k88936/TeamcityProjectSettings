package RustroverProjects.Talkful.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import utils.tauri.applyTauriBuildStep
import utils.tauri.genTauriArtifactRules

object Talkful_Build : BuildType({
    id("Talkful_Build")
    name = "Build"

    vcs {
        root(RustroverProjects.Talkful.vcsRoots.Talkful_GitGithubComK88936talkfulGitRefsHeadsMain)
    }

    artifactRules = genTauriArtifactRules("talkful")

    triggers {
        vcs {
        }
    }
    steps {
        script {
            name = "dep"
            scriptContent = """
                pacman -S alsa-lib --noconfirm
            """.trimIndent()
        }
    }

    applyTauriBuildStep()

    features {
        perfmon { }
    }
})

