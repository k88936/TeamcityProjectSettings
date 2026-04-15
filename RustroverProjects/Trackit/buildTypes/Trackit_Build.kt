package RustroverProjects.Trackit.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Trackit_Build : BuildType({
    id("Trackit_Build")
    name = "Build"

    vcs {
        root(RustroverProjects.Trackit.vcsRoots.Trackit_GitGithubComK88936trackitGitRefsHeadsMain)
    }

    artifactRules = """
        target/release/trackit
        target/x86_64-pc-windows-msvc/release/trackit.exe
    """.trimIndent()

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
                cross build --release --target x86_64-pc-windows-msvc
            """.trimIndent()
        }
    }

    features {
        perfmon { }
    }
})

