package DockerProjects.Nextcloud.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import DockerProjects.DockerBuildTemplate

object Nextcloud_Build : BuildType({
    id("Nextcloud_Build")

    // Use the common Docker build template
    DockerBuildTemplate.createDockerBuild(
        name = "Build",
        dockerfilePath = "Dockerfile",
        imageName = "kvtodev/nextcloud"
    )(this)

    vcs {
        root(DockerProjects.Nextcloud.vcsRoots.Nextcloud_GitGithubComK88936nextcloudGitRefsHeadsMaster)
    }
})