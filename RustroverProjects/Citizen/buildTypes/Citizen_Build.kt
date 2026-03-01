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

    artifactRules = """target/release/citizen"""

    triggers {
        vcs {
        }
    }
    steps {
        script {
            name = "Build"
            scriptContent = """
                cargo build --release
            """.trimIndent()
            dockerImage = "kvtodev/ci-containers:rust"
            dockerRunParameters = getDockerRunParameters()

        }
    }


    features {
        perfmon { }
    }
})
