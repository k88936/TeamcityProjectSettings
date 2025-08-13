package Gold.buildTypes

import jetbrains.buildServer.configs.kotlin.*

object Gold_BuildLinux : BuildType({
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
})
