package RustroverProjects.Source.buildTypes

import RustroverProjects.Source.vcsRoots.Source_GitGithubComK88936sourceGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import utils.deploy.applyGithubPagesDeployment

object Source_Build_Pages : BuildType({
    id("Source_Build_Pages")
    name = "Build Pages"

    vcs {
        root(Source_GitGithubComK88936sourceGitRefsHeadsMain)
    }

    triggers {
        vcs {
        }
    }

    applyGithubPagesDeployment(
        repoSlug = "k88936/Source",
        buildCommand = "pnpm install && node scripts/build-pages.mjs",
        buildDir = "site/dist",
    )

    features {
        perfmon {
        }
    }
})
