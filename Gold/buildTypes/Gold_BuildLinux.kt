package Gold.buildTypes

import jetbrains.buildServer.configs.kotlin.*

object Gold_BuildLinux : BuildType({
    name = "Build_linux"

    vcs {
        root(Gold.vcsRoots.Gold_GitGithubComK88936goldGitRefsHeadsMain)
    }

    steps {
        step {
            id = "cargo"
            type = "cargo"
            param("cargo-command", "build")
        }
    }

    requirements {
        exists("env.PLATFORM_LINUX")
    }
})
