package DockerProjects.Wallence.buildTypes

import DockerProjects.Wallence.vcsRoot
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import utils.docker.applyDockerBuildSteps

object Wallence_Build_container : BuildType({

    id("Wallence_Build_container")
    name = "Build  container"

    vcs {
        root(vcsRoot)
    }

    triggers {
        finishBuildTrigger {
            buildType = Wallence_Build_config_gen.id?.value
            successfulOnly = true
        }
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

    applyDockerBuildSteps("kvtodev/wallence:latest")
})