package RustroverProjects.OllamaProxy.buildTypes

import RustroverProjects.OllamaProxy.vcsRoots.OllamaProxy_GitGithubComK88936ollamaProxyGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.cargo
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object OllamaProxy_Build : BuildType({
    id("OllamaProxy_Build")
    name = "Build"

    artifactRules = "target/release/ollama-proxy.exe"

    vcs {
        root(OllamaProxy_GitGithubComK88936ollamaProxyGitRefsHeadsMain)
    }

    steps {
        cargo {
            id = "cargo"
            command = build {
                release = true
            }
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

    requirements {
        exists("env.PLATFORM_WIN")
    }
})