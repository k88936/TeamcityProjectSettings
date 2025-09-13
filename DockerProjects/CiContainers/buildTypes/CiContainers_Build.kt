package DockerProjects.CiContainers.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import DockerProjects.DockerBuildTemplate

object CiContainers_Build : BuildType({
    id("CiContainers_Build")

    // Use the common Docker build template
    DockerBuildTemplate.createDockerBuild(
        dockerfilePath = "base/yay/Dockerfile",
        imageName = "kvtodev/ci-containers:builder-base"
    )(this)

    vcs {
        root(DockerProjects.CiContainers.vcsRoots.CiContainers_GitGithubComK88936CiContainersGitRefsHeadsMain)
    }
})
