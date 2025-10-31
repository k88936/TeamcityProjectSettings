package DockerProjects.CiContainers

import DockerProjects.CiContainers.vcsRoots.CiContainers_GitGithubComK88936CiContainersGitRefsHeadsMain
import DockerProjects.DockerBuildTemplate
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Project : Project({
    id("CiContainers")
    name = "ci-containers"

    buildType(build("base/Dockerfile", "kvtodev/ci-containers:base"))
    buildType(build("build/rust/Dockerfile", "kvtodev/ci-containers:rust"))
    buildType(build("build/java/Dockerfile", "kvtodev/ci-containers:java"))
    buildType(build("build/js/Dockerfile", "kvtodev/ci-containers:js"))
    buildType(build("build/react-native/Dockerfile", "kvtodev/ci-containers:react-native"))
    buildType(build("ai/continue/Dockerfile", "kvtodev/ci-containers:continue"))

    vcsRoot(CiContainers_GitGithubComK88936CiContainersGitRefsHeadsMain)
})

fun build(dockerfilePath: String, imageName: String): BuildType {
    val formatted = "CiContainers_Build_" + imageName.replace("[^0-9a-zA-Z]".toRegex(), "_")
    return BuildType({
        id(formatted)
        name = formatted
        triggers {
            vcs { }
        }
        DockerBuildTemplate.createDockerBuild(
            imageName = imageName,
            dockerfilePath = dockerfilePath,
        )(this)
        vcs {
            root(CiContainers_GitGithubComK88936CiContainersGitRefsHeadsMain)
        }
    })

}
