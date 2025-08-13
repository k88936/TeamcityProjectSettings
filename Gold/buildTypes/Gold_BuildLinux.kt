package Gold.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.cargo

object Gold_BuildLinux : BuildType({
    name = "Build_linux"

    artifactRules = "target/release/gold"

    vcs {
        root(Gold.vcsRoots.Gold_GitGithubComK88936goldGitRefsHeadsMain)
    }

    steps {
        cargo {
            id = "cargo"
            command = build {
                release = true
            }
        }
    }

    requirements {
        exists("env.PLATFORM_LINUX")
    }
})
