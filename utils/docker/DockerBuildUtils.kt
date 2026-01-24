package utils.docker

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.dockerRegistryConnections
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCommand

fun BuildType.applyDockerBuildSteps(
    imageName: String,
    dockerfilePath: String = "Dockerfile",
    connection: String = "DOCKER_REGISTRY_CONNECTION",
) {
    features {
        perfmon {}
        dockerRegistryConnections {
            loginToRegistry = on {
                dockerRegistryId = connection
            }
        }
    }
    steps {
        dockerCommand {
            id = "build"
            commandType = build {
                source = file {
                    path = dockerfilePath
                }
                namesAndTags = imageName
                commandArgs = "--progress=plain"
            }
        }
        dockerCommand {
            id = "push"
            commandType = push {
                namesAndTags = imageName
                removeImageAfterPush = false
            }
        }
    }
}

