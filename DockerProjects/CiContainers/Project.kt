package DockerProjects.CiContainers

import DockerProjects.CiContainers.buildTypes.*
import DockerProjects.CiContainers.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("CiContainers")
    name = "ci-containers"

    buildType(CiContainers_Build)

    vcsRoot(CiContainers_GitGithubComK88936CiContainersGitRefsHeadsMain)
})
