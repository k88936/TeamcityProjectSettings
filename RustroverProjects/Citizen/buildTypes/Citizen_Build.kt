package RustroverProjects.Citizen.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Citizen_Build : BuildType({
    id("Citizen_Build")
    name = "Build"

    vcs {
        root(RustroverProjects.Citizen.vcsRoots.Citizen_GitGithubComK88936citizenGitRefsHeadsMain)
    }

    artifactRules = """
        target/release/citizen
        target/x86_64-pc-windows-msvc/release/citizen.exe
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
