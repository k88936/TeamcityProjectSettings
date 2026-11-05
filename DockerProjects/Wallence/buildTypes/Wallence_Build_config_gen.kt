package DockerProjects.Wallence.buildTypes

import DockerProjects.Wallence.vcsRoot
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Wallence_Build_config_gen : BuildType({
    id("Wallence_Build_config_gen")
    name = "Build config-gen"
    vcs {
        root(vcsRoot)
    }
    artifactRules = """
        build/config-gen
    """.trimIndent()

    steps {
        script {
            name = "Download Dependencies"
            scriptContent = """
                cargo fetch --locked
            """.trimIndent()
        }

        script {
            name = "Test"
            scriptContent = """
                cargo test --locked
            """.trimIndent()
        }

        script {
            name = "Build config-gen"
            scriptContent = """
                cargo build --release --locked
                mkdir -p build
                cp target/release/config-gen build/config-gen
            """.trimIndent()
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }

})
