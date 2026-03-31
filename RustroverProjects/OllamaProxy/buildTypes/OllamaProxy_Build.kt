package RustroverProjects.OllamaProxy.buildTypes

import RustroverProjects.OllamaProxy.vcsRoots.OllamaProxy_GitGithubComK88936ollamaProxyGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object OllamaProxy_Build : BuildType({
    id("OllamaProxy_Build")
    name = "Build"

    artifactRules = """
        target/release/ollama-proxy
        target/x86_64-pc-windows-gnu/release/ollama-proxy.exe
    """.trimIndent()

    vcs {
        root(OllamaProxy_GitGithubComK88936ollamaProxyGitRefsHeadsMain)
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
                cargo install cross && cross build --release --target x86_64-pc-windows-gnu
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