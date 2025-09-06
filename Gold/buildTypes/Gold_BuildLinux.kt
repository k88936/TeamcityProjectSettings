package Gold.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon

object Gold_BuildLinux : BuildType({
    id("Gold_BuildLinux")
    name = "Build_Linux"

    vcs {
        root(Gold.vcsRoots.Gold_GitGithubComK88936goldGitRefsHeadsMain)
    }

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
