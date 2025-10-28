package Utils.Deploy

import Utils.Version.GithubTemplate
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.BuildTypeSettings
import jetbrains.buildServer.configs.kotlin.VcsRoot
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildFeatures.sshAgent

object GitPushDeployTemplate {

    /**
     * 创建一个Git推送部署构建类型
     */
    fun createGitPushDeployment(
        name: String = "Deploy",
        vcsRoot: VcsRoot? = null,
        cleanCheckout: Boolean = false
    ): BuildType.() -> Unit {
        return {
            this.name = name
            this.enablePersonalBuilds = false
            this.type = BuildTypeSettings.Type.DEPLOYMENT
            this.maxRunningBuilds = 1

            // 如果提供了 vcsRoot，则配置 VCS 设置
            if (vcsRoot != null) {
                vcs {
                    root(vcsRoot)
                    this.cleanCheckout = cleanCheckout
                }
            }
            GithubTemplate.createGitPushStep()(this)
            features {
                sshAgent {
                    teamcitySshKey = "id_rsa"
                }
                perfmon {
                }
            }
        }
    }
}
