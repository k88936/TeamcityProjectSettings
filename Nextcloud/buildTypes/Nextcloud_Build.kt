package Nextcloud.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.BuildType
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
        script {
            id = "simpleRunner"
            scriptContent = "./generate-stackbrew-library.sh"
        }
        script {
            id = "simpleRunner_1"
            scriptContent = "./update.sh"
        }
        dockerCommand {
            id = "DockerCommand"
            commandType = build {
                source = file {
                    path = "31/apache/Dockerfile"
                }
                namesAndTags = "kvtodev/nextcloud:latest"
            }
        }
        dockerCommand {
            id = "DockerCommand_1"
            commandType = push {
                namesAndTags = "kvtodev/nextcloud:latest"
                removeImageAfterPush = false
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
})