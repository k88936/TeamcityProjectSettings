package RustroverProjects.Rustdesk.buildTypes

import RustroverProjects.Rustdesk.vcsRoots.Rustdesk_GitGithubComK88936rustdeskGitRefsHeadsMaster
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Rustdesk_Build : BuildType({
    id("Rustdesk_Build")
    name = "Build"

    artifactRules = "target/release/rustdesk"

    vcs {
        root(Rustdesk_GitGithubComK88936rustdeskGitRefsHeadsMaster)
    }

    steps {
        dockerCommand {
            id = "build build image"
            commandType = build {
                source = file {
                    path = "./Dockerfile"
                }
                namesAndTags = "rustdesk-builder"
                commandArgs = "--progress=plain"
            }
        }
        script {
            scriptContent = """
                sh -l entrypoint.sh --release
            """.trimIndent()
            dockerImage = "rustdesk-builder"
            dockerRunParameters =
                "--rm -v rustdesk-git-cache:/home/user/.cargo/git -v rustdesk-registry-cache:/home/user/.cargo/registry -e PUID=\"\$(id -u)\" -e PGID=\"\$(id -g)\""
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
    }
})