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
                 mkdir -p /usr/lib/gdk-pixbuf-2.0/2.10.0/loaders
                 gdk-pixbuf-query-loaders > /usr/lib/gdk-pixbuf-2.0/2.10.0/loaders.cache
                pacman -S alsa-lib gtk3 webkit2gtk-4.1  openssl appmenu-gtk-module libappindicator-gtk3 librsvg xdotool -noconfirm
            """.trimIndent()
        }
    }

    applyTauriBuildStep()

    features {
        perfmon { }
    }
})

