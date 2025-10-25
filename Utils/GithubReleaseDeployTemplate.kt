package Utils

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script


object GithubReleaseDeployTemplate {
    /**
     * 创建一个GitHub Release部署构建类型
     */
    fun createGithubReleaseDeployment(
        tagPattern: String = "v%build.number%",
        notes: String? = null,
        vcsRoot: VcsRoot,
        assetsPath: String = "_deploy/*",
    ): BuildType.() -> Unit {
        return {

            val scriptContent = buildString {
                append("gh release create $tagPattern")
                if (notes != null) {
                    append(" --notes \"$notes\"")
                } else {
                    append(" --generate-notes")
                }
                append(" $assetsPath")
            }

            vcs {
                root(vcsRoot)
            }
            steps {
                script {
                    this.name = "Create GitHub Release"
                    this.scriptContent = scriptContent
                }
            }
            params {
                password("env.GH_TOKEN", "credentialsJSON:04d96fb0-dbf8-457b-be29-2327ab11dd68")
            }
            requirements{
                exists("env.GH_CLI")
            }
        }
    }

}