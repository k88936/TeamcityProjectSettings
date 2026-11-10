package utils.docker

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.dockerRegistryConnections
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script

fun BuildType.applyDockerBuildSteps(
    imageName: String,
    tags: List<String> = listOf("latest"),
    dockerfilePath: String = "Dockerfile",
    context: String = "",
    connection: String = PRIVATE_REGISTRY_CONNECTION,
) {
    require(tags.isNotEmpty()) { "At least one tag is required for $imageName" }

    val taggedImages = tags.distinct().map { tag -> "$imageName:$tag" }
    val tagArguments = taggedImages.joinToString(separator = " \\\n                    ") { "-t $it" }
    val contextArgument = if (context.isBlank()) "" else " \\\n                    $context"
    val pushCommands = taggedImages.joinToString(separator = "\n                ") { "podman push $it" }

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
    val noProxy = "localhost,127.0.0.1,::1,.local,.svc,.svc.cluster.local,teamcity.teamcity,teamcity.k88936.top"
    val jvmProxy = "-Dhttp.proxyHost=$proxyHost -Dhttp.proxyPort=$proxyPort " +
        "-Dhttps.proxyHost=$proxyHost -Dhttps.proxyPort=$proxyPort " +
        "-Dhttp.nonProxyHosts=localhost|127.0.0.1|*.local|*.svc|*.svc.cluster.local|teamcity.teamcity|teamcity.k88936.top"

    steps {
        script {
            id = "build"
            name = "Build with Podman"
            scriptContent = """
                set -e
                podman build \
                    --progress=plain \
                    --build-arg NO_PROXY="$noProxy" \
                    --build-arg no_proxy="$noProxy" \
                    --build-arg HTTP_PROXY=$proxyUrl \
                    --build-arg http_proxy=$proxyUrl \
                    --build-arg HTTPS_PROXY=$proxyUrl \
                    --build-arg https_proxy=$proxyUrl \
                    --build-arg GRADLE_OPTS="$jvmProxy" \
                    --build-arg JAVA_TOOL_OPTIONS="$jvmProxy" \
                    -f $dockerfilePath \
                    $tagArguments$contextArgument
            """.trimIndent()
        }
        script {
            id = "push"
            name = "Push with Podman"
            scriptContent = """
                set -e
                $pushCommands
            """.trimIndent()
        }
    }
}

const val PRIVATE_REGISTRY_HOST = "gitea.k88936.top:8936"

const val PRIVATE_REGISTRY_CONNECTION = "PRIVATE_REGISTRY_CONNECTION"
