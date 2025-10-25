package RustroverProjects.Gold.buildTypes

import RustroverProjects.Gold.vcsRoots.Gold_GitGithubComK88936goldGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon

object Gold_Deploy : BuildType({
    id("Gold_Deploy")
    name = "Deploy"
    type = Type.DEPLOYMENT

    steps {
        step {
            id = "cargo"
            type = "cargo"
            param("cargo-command", "build")
            param("cargo-build-release", "true")
        }
    }

    requirements {
        exists("env.PLATFORM_LINUX")
    }

    features{
        perfmon {  }
    }
})
