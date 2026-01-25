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

) {
    features {
        perfmon {}
        dockerRegistryConnections {
            loginToRegistry = on {
                dockerRegistryId = connection
            }
        }
    }

    val proxyHost = "wallence.wallence.svc.cluster.local"
    val proxyPort = "7890"
    val proxyUrl = "http://$proxyHost:$proxyPort"
    val proxy_args = """
        --build-arg NO_PROXY="localhost,127.0.0.1,::1,$proxyHost,.local" HTTP_PROXY=$proxyUrl HTTPS_PROXY=$proxyUrl
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

