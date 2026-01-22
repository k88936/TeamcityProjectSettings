package DockerProjects

import jetbrains.buildServer.configs.kotlin.Project


object Project : Project({

    name = "Docker Projects"
    id("DockerProjects")

    subProject(DockerProjects.TeamcityAgent.Project)
    subProject(DockerProjects.Nextcloud.Project)
    subProject(DockerProjects.CiContainers.Project)
})