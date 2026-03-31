package DockerProjects.CiContainers

import DockerProjects.CiContainers.vcsRoots.CiContainers_GitGithubComK88936CiContainersGitRefsHeadsMain
import DockerProjects.DockerBuildTemplate
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("CiContainers")
    name = "ci-containers"

    buildType(build("base/Dockerfile", "docker.io/kvtodev/ci-containers:base"))
    buildType(build("build/rust/Dockerfile", "docker.io/kvtodev/ci-containers:rust"))
    buildType(build("build/java/Dockerfile", "docker.io/kvtodev/ci-containers:java"))
    buildType(build("build/go/Dockerfile", "docker.io/kvtodev/ci-containers:go"))
    buildType(build("build/js/Dockerfile", "docker.io/kvtodev/ci-containers:js"))
    buildType(build("build/react-native/Dockerfile", "docker.io/kvtodev/ci-containers:react-native"))
    buildType(build("build/android/Dockerfile", "docker.io/kvtodev/ci-containers:android"))
    buildType(build("ai/opencode/Dockerfile", "docker.io/kvtodev/ci-containers:opencode"))

    vcsRoot(CiContainers_GitGithubComK88936CiContainersGitRefsHeadsMain)
})

fun build(dockerfilePath: String, imageName: String): BuildType {
    val formatted = "CiContainers_Build_" + imageName.replace("[^0-9a-zA-Z]".toRegex(), "_")
    return DockerBuildTemplate(
        name = formatted,
        imageName = imageName,
        dockerfilePath = dockerfilePath,
        vcsRoot = CiContainers_GitGithubComK88936CiContainersGitRefsHeadsMain,
    )
}
