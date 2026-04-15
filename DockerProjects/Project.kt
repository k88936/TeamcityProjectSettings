package DockerProjects

import jetbrains.buildServer.configs.kotlin.Project


object Project : Project({

    name = "Docker Projects"
    id("DockerProjects")

    subProject(DockerProjects.CiContainers.Project)
    subProject(DockerProjects.Wallence.Project)
    subProject(
        DockerProjectTemplate(
            "git@github.com:k88936/teamcity-agent-container.git",
            "teamcity-agent/Dockerfile",
            "docker.io/kvtodev/teamcity-agent"
        )
    )
})