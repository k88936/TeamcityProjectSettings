package utils.deploy

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import utils.Env

fun BuildType.applyGithubReleaseDeployment(
    tagPattern: String = "v${Env.BUILD_NUMBER}",
    notes: String? = null,
    assetsPath: String = "_deploy/*",
    prerelease: Boolean = false
) {
    var extra_param = ""
    if (prerelease) {
        extra_param += "--prerelease"
    }
    val scriptContent = buildString {
        append("gh release create $extra_param --target ${Env.BUILD_BRANCH} $tagPattern")
        if (notes != null) {
            append(" --notes \"$notes\"")
        } else {
            append(" --generate-notes")
        }
        append(" $assetsPath")
    }

    require(!this.vcs.entries.isEmpty())
    steps {
        script {
            this.name = "Create GitHub Release"
            this.scriptContent = scriptContent
        }
    }
    params {
        password("env.GH_TOKEN", "credentialsJSON:04d96fb0-dbf8-457b-be29-2327ab11dd68")
    }
    requirements {
        exists("env.GH_CLI")
    }
}