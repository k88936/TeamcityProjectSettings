package Utils.Deploy

import Utils.Env
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.script

object GithubReleaseDeployTemplate {
    /**
     * 创建一个GitHub Release部署构建类型
     */
    fun createGithubReleaseDeployment(
        tagPattern: String = "v%build.number%",
        notes: String? = null,
        assetsPath: String = "_deploy/*",
        prerelease: Boolean = false
    ): BuildType.() -> Unit {
        return {

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
    }

}