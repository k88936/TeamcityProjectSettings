package DockerProjects.Dockerfiles

import DockerProjects.DockerBuildTemplate
import DockerProjects.Dockerfiles.vcsRoots.Dockerfiles_GitGithubComK88936DockerfilessGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("Dockerfiles")
    name = "dockerfiles"

    buildType(build("nextcloud/Dockerfile", "docker.io/kvtodev/nextcloud"))
    buildType(build("teamcity-agent/Dockerfile", "docker.io/kvtodev/teamcity-agent"))

    vcsRoot(Dockerfiles_GitGithubComK88936DockerfilessGitRefsHeadsMain)
})

fun build(dockerfilePath: String, imageName: String): BuildType {
    val formatted = "Dockerfiles_Build_" + imageName.replace("[^0-9a-zA-Z]".toRegex(), "_")
    return DockerBuildTemplate(
        name = formatted,
        imageName = imageName,
        dockerfilePath = dockerfilePath,
        vcsRoot = Dockerfiles_GitGithubComK88936DockerfilessGitRefsHeadsMain,
    )
}
