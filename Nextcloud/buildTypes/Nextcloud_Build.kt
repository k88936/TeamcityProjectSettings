package Nextcloud.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.dockerRegistryConnections
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Nextcloud_Build : BuildType({
    id("Nextcloud_Build")
    name = "Build"

    vcs {
        root(Nextcloud.vcsRoots.Nextcloud_GitGithubComK88936nextcloudGitRefsHeadsMaster)
    }

    steps {
        dockerCommand {
            id = "DockerCommand"
            commandType = build {
                source = file {
                    path = "Dockerfile"
                }
                contextDir = ""
                platform = jetbrains.buildServer.configs.kotlin.buildSteps.DockerCommandStep.ImagePlatform.Any
                namesAndTags = "kvtodev/nextcloud"
                commandArgs = ""
            }
        }
        dockerCommand {
            id = "DockerCommand_1"
            commandType = push {
                namesAndTags = "kvtodev/nextcloud"
                removeImageAfterPush = false
                commandArgs = ""
            }
        }
    }

    triggers {
        vcs {}
    }

    features {
        perfmon {}
        dockerRegistryConnections {
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_3"
            }
        }
    }
})