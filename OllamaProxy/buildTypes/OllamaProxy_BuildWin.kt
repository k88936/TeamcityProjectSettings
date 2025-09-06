package OllamaProxy.buildTypes

import OllamaProxy.vcsRoots.OllamaProxy_GitGithubComK88936ollamaProxyGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.cargo
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object OllamaProxy_BuildWin : BuildType({
    id("OllamaProxy_BuildWin")
    name = "Build_Win"

    artifactRules = """target\release\ollama-proxy.exe"""

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