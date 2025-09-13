package DockerProjects.CiContainers

import DockerProjects.CiContainers.vcsRoots.*
import DockerProjects.DockerBuildTemplate
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("CiContainers")
    name = "ci-containers"

    buildType(build("base/yay/Dockerfile", "kvtodev/ci-containers:builder-base"))
    buildType(build("build/rust/Dockerfile", "kvtodev/ci-containers:rust"))
    buildType(build("build/flutter/Dockerfile", "kvtodev/ci-containers:flutter"))

    vcsRoot(CiContainers_GitGithubComK88936CiContainersGitRefsHeadsMain)
})

fun build(dockerfilePath: String, imageName: String): BuildType {
    val formatted = "CiContainers_Build_" + imageName.replace("[^0-9a-zA-Z]".toRegex(), "_")
    return BuildType({
        id(formatted)
        DockerBuildTemplate.createDockerBuild(
            name = formatted,
            dockerfilePath = dockerfilePath,
            imageName = imageName
        )(this)
        vcs {
            root(CiContainers_GitGithubComK88936CiContainersGitRefsHeadsMain)
        }
    })

}
