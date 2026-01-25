package utils.docker

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.dockerRegistryConnections
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCommand

fun BuildType.applyDockerBuildSteps(
    imageName: String,
    dockerfilePath: String = "Dockerfile",
    context: String = "",
    connection: String = "DOCKER_REGISTRY_CONNECTION",
    proxy: String = "http://wallence.wallence.svc.cluster.local"

) {
    features {
        perfmon {}
        dockerRegistryConnections {
            loginToRegistry = on {
                dockerRegistryId = connection
            }
        }
    }

    val proxy_args = """
        --build-arg NO_PROXY="localhost,127.0.0.1,::1,.local" HTTP_PROXY=$proxy HTTPS_PROXY=$proxy
    """.trimIndent()
    steps {
        dockerCommand {
            id = "build"
            commandType = build {
                source = file {
                    path = dockerfilePath
                }
                contextDir = context
                namesAndTags = imageName
                commandArgs = "--progress=plain $proxy_args"
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

