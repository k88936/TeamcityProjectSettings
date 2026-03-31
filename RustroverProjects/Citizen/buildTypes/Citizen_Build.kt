package RustroverProjects.Citizen.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import utils.build_steps.getDockerRunParameters

object Citizen_Build : BuildType({
    id("Citizen_Build")
    name = "Build"

    vcs {
        root(RustroverProjects.Citizen.vcsRoots.Citizen_GitGithubComK88936citizenGitRefsHeadsMain)
    }

//    target/x86_64-pc-windows-gnu/release/citizen.exe
    artifactRules = """
        target/release/citizen
    """.trimIndent()

    triggers {
        vcs {
        }
    }
    steps {
        script {
            name = "Build Linux"
            scriptContent = """
                cargo build --release
            """.trimIndent()
            dockerImage = "docker.io/kvtodev/ci-containers:rust"
            dockerRunParameters = getDockerRunParameters()
        }
//        script {
//            name = "Build Windows"
//            scriptContent = """
//                cargo install cross && cross build --release --target x86_64-pc-windows-gnu
//            """.trimIndent()
//            dockerImage = "docker.io/kvtodev/ci-containers:rust"
//            dockerRunParameters = getDockerRunParameters()
//        }
    }

    features {
        perfmon { }
    }
})
