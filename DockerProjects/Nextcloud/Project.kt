package DockerProjects.Nextcloud

import DockerProjects.DockerBuildTemplate
import DockerProjects.Nextcloud.vcsRoots.Nextcloud_GitGithubComK88936nextcloudGitRefsHeadsMaster
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("Nextcloud")
    name = "Nextcloud"

    vcsRoot(Nextcloud_GitGithubComK88936nextcloudGitRefsHeadsMaster)

    buildType(
        DockerBuildTemplate(
            name = "Nextcloud",
            imageName = "kvtodev/nextcloud",
            vcsRoot = Nextcloud_GitGithubComK88936nextcloudGitRefsHeadsMaster,
            enableVcsTrigger = true
        )
    )

})
