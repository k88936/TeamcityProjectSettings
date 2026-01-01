package DockerProjects

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.dockerRegistryConnections
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.triggers.vcs


fun DockerBuildTemplate(
    name: String,
    imageName: String,
    dockerfilePath: String = "Dockerfile",
    connection: String = "DOCKER_REGISTRY_CONNECTION",
    vcsRoot: jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot? = null,
    enableVcsTrigger: Boolean = true
): BuildType {
    return BuildType({
        id(name)
        this.name = name

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
        features {
            perfmon {}
            dockerRegistryConnections {
                loginToRegistry = on {
                    dockerRegistryId = connection
                }
            }
        }

        vcsRoot?.let { root ->
            vcs {
                root(root)
            }
        }

        if (enableVcsTrigger) {
            triggers {
                vcs { }
            }
        }
    })
}
