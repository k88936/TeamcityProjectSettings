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
        build/=>build/
    """.trimIndent()

    triggers {
        vcs {
        }
    }

    steps {
        script {
            name = "Download Dependencies"
            scriptContent = """
                source /etc/profile
                go mod download
                go mod verify
            """.trimIndent()
            dockerImage = "kvtodev/ci-containers:go"
            dockerRunParameters =
                "--rm -v /cache/go:/go"
            dockerPull = true
        }

//        script {
//            name = "Test"
//            scriptContent = """
//                source /etc/profile
//                go test ./config-gen
//            """.trimIndent()
//            dockerImage = "kvtodev/ci-containers:go"
//            dockerRunParameters =
//                "--rm -v /cache/go:/go"
//            dockerPull = true
//        }

        script {
            name = "Build config-gen"
            scriptContent = """
                source /etc/profile
                go build -o build/config-gen ./config-gen
            """.trimIndent()
            dockerImage = "kvtodev/ci-containers:go"
            dockerRunParameters =
                "--rm -v /cache/go:/go"
            dockerPull = true
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