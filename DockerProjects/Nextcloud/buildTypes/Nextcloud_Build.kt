package DockerProjects.Nextcloud.buildTypes

import DockerProjects.DockerBuildTemplate
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Nextcloud_Build : BuildType({
    id("Nextcloud_Build")

    name = "Nextcloud"
    // Use the common Docker build template
    DockerBuildTemplate.createDockerBuild(
        imageName = "kvtodev/nextcloud",
    )(this)


    vcs {
        root(DockerProjects.Nextcloud.vcsRoots.Nextcloud_GitGithubComK88936nextcloudGitRefsHeadsMaster)
    }
    triggers {
        vcs { }
    }
})