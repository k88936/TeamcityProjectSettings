package DockerProjects.Nextcloud.buildTypes

import DockerProjects.DockerBuildTemplate
import jetbrains.buildServer.configs.kotlin.BuildType

object Nextcloud_Build : BuildType({
    id("Nextcloud_Build")

    // Use the common Docker build template
    DockerBuildTemplate.createDockerBuild(
        imageName = "kvtodev/nextcloud",
    )(this)

    vcs {
        root(DockerProjects.Nextcloud.vcsRoots.Nextcloud_GitGithubComK88936nextcloudGitRefsHeadsMaster)
    }
})