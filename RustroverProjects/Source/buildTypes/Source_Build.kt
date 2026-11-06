package RustroverProjects.Source.buildTypes

import RustroverProjects.Source.vcsRoots.Source_GitGithubComK88936sourceGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.FailureAction
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Source_Build : BuildType({
    id("Source_Build")
    name = "Build"
    type = Type.COMPOSITE

    vcs {
        root(Source_GitGithubComK88936sourceGitRefsHeadsMain)
        showDependenciesChanges = true
    }

    triggers {
        vcs {
        }
    }

    dependencies {
        snapshot(Source_Build_Linux) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
        snapshot(Source_Build_Windows) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
        snapshot(Source_Build_Android) {
            onDependencyFailure = FailureAction.FAIL_TO_START
        }
    }

    features {
        perfmon { }
    }
})
