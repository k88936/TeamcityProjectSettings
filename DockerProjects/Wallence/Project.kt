package DockerProjects.Wallence

import DockerProjects.Wallence.buildTypes.Wallence_Build_config_gen
import DockerProjects.Wallence.buildTypes.Wallence_Build_container
import jetbrains.buildServer.configs.kotlin.Project
import utils.vcs.GitVcsRootTemplate

val vcsRoot = GitVcsRootTemplate("git@github.com:k88936/wallence.git")

object Project : Project({
    id("Wallence")
    name = "Wallence"

    vcsRoot(vcsRoot)

    buildType(Wallence_Build_config_gen)
    buildType(Wallence_Build_container)

})