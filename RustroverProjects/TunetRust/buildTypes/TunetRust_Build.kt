package RustroverProjects.TunetRust.buildTypes

import RustroverProjects.TunetRust.vcsRoots.TunetRust_GitGithubComK88936tunetRustGitRefsHeadsMaster
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.cargo
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object TunetRust_Build : BuildType({
    id("TunetRust_Build")
    name = "Build"

    artifactRules = "target/release/tunet-rust.exe"

    vcs {
        root(TunetRust_GitGithubComK88936tunetRustGitRefsHeadsMaster)
    }

    steps {
        cargo {
            id = "cargo"
            command = build {
                release = true
                buildPackage = "tunet tunet-service"
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
