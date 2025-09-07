package DockerProjects

import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.dockerRegistry


object Project: Project({

    name = "Docker Projects"
    id("DockerProjects")

    subProject(DockerProjects.TeamcityAgent.Project)
    subProject(DockerProjects.Nextcloud.Project)
    features {
        dockerRegistry {
            id = "DOCKER_REGISTRY_CONNECTION"
            name = "Docker Registry"
            userName = "kvtodev"
            password = "credentialsJSON:a43b4956-2b9e-457c-bf3c-6e04db2adc38"
        }
    }
})