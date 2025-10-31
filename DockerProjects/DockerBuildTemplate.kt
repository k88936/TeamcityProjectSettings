package DockerProjects

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.dockerRegistryConnections
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object DockerBuildTemplate {
    /**
     * 创建一个标准的Docker构建类型
     */
    fun createDockerBuild(
        imageName: String,
        dockerfilePath: String = "Dockerfile",
        name: String = "Build",
        connection: String = "DOCKER_REGISTRY_CONNECTION",

        ): BuildType.() -> Unit {
        return {
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

            triggers {
                vcs {}
            }

            features {
                perfmon {}
                dockerRegistryConnections {
                    loginToRegistry = on {
                        dockerRegistryId = connection
                    }
                }
            }
        }
    }
}