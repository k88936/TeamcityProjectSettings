package DockerProjects

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import utils.docker.applyDockerBuildSteps

fun DockerBuildTemplate(
    name: String,
    imageName: String,
    dockerfilePath: String = "Dockerfile",
    vcsRoot: jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot,
): BuildType {
    return BuildType({
        id(name)
        this.name = name

        applyDockerBuildSteps(imageName, dockerfilePath)
        vcs {
            root(vcsRoot)
        }
        triggers {
            vcs { }
        }
    })
}
