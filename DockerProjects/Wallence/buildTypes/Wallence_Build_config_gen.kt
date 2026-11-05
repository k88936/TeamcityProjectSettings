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
            name = "Build config-gen"
            scriptContent = """
                cargo rustc --release --locked --target x86_64-unknown-linux-gnu --bin config-gen -- -C target-feature=+crt-static
                mkdir -p build
                cp target/x86_64-unknown-linux-gnu/release/config-gen build/config-gen
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
