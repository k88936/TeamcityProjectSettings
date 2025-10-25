package RustroverProjects.Gold.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Gold_Build : BuildType({
    id("Gold_Build")
    name = "Build"

    vcs {
        root(RustroverProjects.Gold.vcsRoots.Gold_GitGithubComK88936goldGitRefsHeadsMain)
    }

    artifactRules = """target/release/gold"""

    triggers {
        vcs {
        }
    }
    steps {
        script {
            name = "Build"
            scriptContent = """
                source /etc/profile
                rustup default stable
                cargo build --release
            """.trimIndent()
            dockerImage = "kvtodev/ci-containers:rust"

        }
    }


    features {
        perfmon { }
    }
})
