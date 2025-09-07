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

    features {
        dockerRegistry {
            id = "PROJECT_EXT_3"
            name = "Docker Registry"
            userName = "kvtodev"
            password = "credentialsJSON:a43b4956-2b9e-457c-bf3c-6e04db2adc38"
        }
    }
})
