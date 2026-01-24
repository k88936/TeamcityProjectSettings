package DockerProjects.Wallence.buildTypes

import DockerProjects.Wallence.vcsRoot
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import utils.docker.applyDockerBuildSteps

object Wallence_Build_container : BuildType({

    id("BuildWallence_Build_container")
    name = "Build Wallence container"

    vcs {
        root(vcsRoot)
    }

    triggers {
        vcs { }
    }
    features {
        perfmon { }
    }

    dependencies {
        artifacts(Wallence_Build_config_gen) {
            buildRule = lastSuccessful()
            artifactRules = """
                build/=>build/
            """.trimIndent()
        }
    }

    applyDockerBuildSteps("kvtodev/wallence")
})