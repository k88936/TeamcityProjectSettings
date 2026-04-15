package RustroverProjects.Trackit.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import utils.genRustArtifactRules

object Trackit_Build : BuildType({
    id("Trackit_Build")
    name = "Build"

    vcs {
        root(RustroverProjects.Trackit.vcsRoots.Trackit_GitGithubComK88936trackitGitRefsHeadsMain)
    }

    artifactRules = genRustArtifactRules("trackit")

    triggers {
        vcs {
        }
    }
    steps {
        script {
            name = "Build Linux"
            scriptContent = """
                cargo build --release
            """.trimIndent()
        }
        script {
            name = "Build Windows"
            scriptContent = """
                cross build --release --target x86_64-pc-windows-gnu
            """.trimIndent()
        }
    }

    features {
        perfmon { }
    }
})

