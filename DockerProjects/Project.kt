package DockerProjects

import jetbrains.buildServer.configs.kotlin.Project


object Project : Project({

    name = "Docker Projects"
    id("DockerProjects")

    subProject(DockerProjects.CiContainers.Project)
    subProject(DockerProjects.Wallence.Project)
    subProject(DockerProjects.Dockerfiles.Project)
})