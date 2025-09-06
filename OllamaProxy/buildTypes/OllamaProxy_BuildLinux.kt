package OllamaProxy.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.cargo
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object OllamaProxy_BuildLinux : BuildType({
    id("OllamaProxy_BuildLinux")
    name = "Build_Linux"

    vcs {
        root(OllamaProxy.vcsRoots.OllamaProxy_GitGithubComK88936ollamaProxyGitRefsHeadsMain)
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
        exists("env.PLATFORM_LINUX")
    }
})