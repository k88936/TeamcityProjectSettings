package Utils

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script


object GithubReleaseDeployTemplate {
    /**
     * 创建一个GitHub Release部署构建类型
     */
    fun createGithubReleaseDeployment(
        name: String = "Deploy",
        tagPattern: String = "v%build.number%",
        notes: String? = null,
        vcsRoot: VcsRoot,
        assetsPath: String = "*"
    ): BuildType.() -> Unit {
        return {
            this.name = name
            this.enablePersonalBuilds = false
            this.type = BuildTypeSettings.Type.DEPLOYMENT
            this.maxRunningBuilds = 1

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
                    id = "github_release"
                    this.scriptContent = scriptContent
                }
            }
            params {
                password("env.GH_TOKEN", "credentialsJSON:04d96fb0-dbf8-457b-be29-2327ab11dd68")
            }
        }
    }

}