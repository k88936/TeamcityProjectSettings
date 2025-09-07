package DockerProjects

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.dockerRegistryConnections
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object DockerBuildTemplate {
    /**
     * 创建一个标准的Docker构建类型
     */
    fun createDockerBuild(
        name: String = "Build",
        dockerfilePath: String = "Dockerfile",
        imageName: String = "kvtodev/image-name",
        contextDir: String = "",
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
                        dockerRegistryId = "DOCKER_REGISTRY_CONNECTION"
                    }
                }
            }
        }
    }
}