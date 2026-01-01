package DockerProjects.Nextcloud.buildTypes

import DockerProjects.applyDockerBuild
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Nextcloud_Build : BuildType({
    id("Nextcloud_Build")

    name = "Nextcloud"
    applyDockerBuild(imageName = "kvtodev/nextcloud")

    vcs {
        root(DockerProjects.Nextcloud.vcsRoots.Nextcloud_GitGithubComK88936nextcloudGitRefsHeadsMaster)
    }
    triggers {
        vcs { }
    }
})