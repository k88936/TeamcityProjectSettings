package DockerProjects.Nextcloud

import DockerProjects.Nextcloud.buildTypes.*
import DockerProjects.Nextcloud.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.dockerRegistry

object Project : Project({
    id("Nextcloud")
    name = "Nextcloud"

    vcsRoot(Nextcloud_GitGithubComK88936nextcloudGitRefsHeadsMaster)

    buildType(Nextcloud_Build)

})
