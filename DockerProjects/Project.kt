package DockerProjects

import jetbrains.buildServer.configs.kotlin.Project
import utils.Env
import utils.docker.PRIVATE_REGISTRY_CONNECTION
import utils.docker.PRIVATE_REGISTRY_HOST


object Project : Project({

    name = "Docker Projects"
    id("DockerProjects")

    subProject(DockerProjects.Wallence.Project)
    subProject(
        DockerProjectTemplate(
            "git@github.com:k88936/teamcity-agent-container.git",
            "teamcity-agent/Dockerfile",
            "$PRIVATE_REGISTRY_HOST/k88936/teamcity-agent",
            tags = listOf(Env.BUILD_NUMBER, "latest"),
            connection = PRIVATE_REGISTRY_CONNECTION
        )
    )
})
