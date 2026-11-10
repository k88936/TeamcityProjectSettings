package DockerProjects

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot
import utils.docker.applyDockerBuildSteps

fun DockerBuildTemplate(
    id: String,
    name: String,
    imageName: String,
    tags: List<String> = listOf("latest"),
    dockerfilePath: String = "Dockerfile",
    vcsRoot: GitVcsRoot,
    connection: String,
): BuildType {
    return BuildType({
        id(id)
        this.name = name

        applyDockerBuildSteps(
            imageName = imageName,
            tags = tags,
            dockerfilePath = dockerfilePath,
            connection = connection,
        )
        vcs {
            root(vcsRoot)
        }
        triggers {
            vcs { }
        }
    })
}
