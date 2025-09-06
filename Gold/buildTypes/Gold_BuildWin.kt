package Gold.buildTypes

import jetbrains.buildServer.configs.kotlin.*

object Gold_BuildWin : BuildType({
    id("Gold_BuildWin")
    name = "Build_Win"

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
        exists("env.PLATFORM_WIN")
    }

})
