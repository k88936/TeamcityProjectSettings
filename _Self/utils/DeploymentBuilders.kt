package _Self.utils

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script

object DeploymentBuilders {
    
    /**
     * 创建一个GitHub Release部署构建类型
     */
    fun createGithubReleaseDeployment(
        name: String = "Deploy",
        tagPattern: String = "build-%build.number%",
        generateNotes: Boolean = true,
        notes: String? = null,
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
                } else if (generateNotes) {
                    append(" --generate-notes")
                }
                append(" $assetsPath")
            }
            
            steps {
                script {
                    this.name = "Create GitHub Release"
                    id = "github_release"
                    this.scriptContent = scriptContent
                }
            }
        }
    }
    
    /**
     * 创建一个Git推送部署构建类型
     */
    fun createGitPushDeployment(
        name: String = "Deploy"
    ): BuildType.() -> Unit {
        return {
            this.name = name
            this.enablePersonalBuilds = false
            this.type = BuildTypeSettings.Type.DEPLOYMENT
            this.maxRunningBuilds = 1
            
            steps {
                script {
                    this.name = "Git Push Changes"
                    id = "git_push"
                    this.scriptContent = """
                        git add -A
                        git commit -m"update"
                        git push --force
                    """.trimIndent()
                }
            }
        }
    }
}