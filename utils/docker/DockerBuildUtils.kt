package utils.docker

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.dockerRegistryConnections
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script

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

    steps {
        script {
            id = "build"
            name = "Build with Podman"
            scriptContent = """
                set -e
                podman build \
                    --progress=plain \
                    --build-arg NO_PROXY="localhost,127.0.0.1,::1,$proxyHost,.local" \
                    --build-arg no_proxy="localhost,127.0.0.1,::1,$proxyHost,.local" \
                    --build-arg HTTP_PROXY=$proxyUrl \
                    --build-arg http_proxy=$proxyUrl \
                    --build-arg HTTPS_PROXY=$proxyUrl \
                    --build-arg https_proxy=$proxyUrl \
                    -f $dockerfilePath \
                    -t $imageName \
                    $context
            """.trimIndent()
        }
        script {
            id = "push"
            name = "Push with Podman"
            scriptContent = """
                set -e
                podman push $imageName
            """.trimIndent()
        }
    }
}

