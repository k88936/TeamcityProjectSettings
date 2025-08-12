package Gold.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Gold_Build : BuildType({
    name = "Build"

    vcs {
        root(Gold.vcsRoots.Gold_GitGithubComK88936goldGitRefsHeadsMain)
    }

    steps {
        step {
            id = "cargo"
            type = "cargo"
            param("cargo-command", "build")
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
