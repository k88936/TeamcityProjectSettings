package RustroverProjects.TunetRust.buildTypes

import RustroverProjects.TunetRust.vcsRoots.TunetRust_GitGithubComK88936tunetRustGitRefsHeadsMaster
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object TunetRust_Build : BuildType({
    id("TunetRust_Build")
    name = "Build"

    artifactRules = """
        target/release/tunet
        target/release/tunet-service
        target/x86_64-pc-windows-gnu/release/tunet.exe
        target/x86_64-pc-windows-gnu/release/tunet-service.exe
    """.trimIndent()

    vcs {
        root(TunetRust_GitGithubComK88936tunetRustGitRefsHeadsMaster)
    }

    steps {
        script {
            name = "Build Linux"
            scriptContent = """
                cargo build --release -p tunet -p tunet-service
            """.trimIndent()
        }
        script {
            name = "Build Windows"
            scriptContent = """
                cargo install cross && cross build --release --target x86_64-pc-windows-gnu -p tunet -p tunet-service
            """.trimIndent()
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
