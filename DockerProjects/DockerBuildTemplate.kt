package DockerProjects

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot
import utils.docker.applyDockerBuildSteps

fun DockerBuildTemplate(
    name: String,
    imageName: String,
    dockerfilePath: String = "Dockerfile",
    vcsRoot: GitVcsRoot,
    context: String,
    connection: String,
): BuildType {
    return BuildType({
        id(name)
        this.name = name

        applyDockerBuildSteps(imageName, dockerfilePath, context, connection)
        vcs {
            root(vcsRoot)
        }
        triggers {
            vcs { }
        }
    })
}
