package utils

import Fernflower.buildTypes.Fernflower_Deploy.params
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildFeatures.sshAgent
import jetbrains.buildServer.configs.kotlin.buildSteps.script

object DeploymentBuilders {

    /**
     * 创建一个构建 Qt Windows 应用程序的构建类型
     */
    fun createQtWindowsBuild(
        name: String = "Build",
        qtInstallPath: String = "C:\\Qt\\6.9.1\\msvc2022_64",
        buildConfig: String = "Release",
        executableName: String = "Application"
    ): BuildType.() -> Unit {
        return {
            this.name = name
            this.artifactRules = "build/${executableName}.zip"

            params {
                text("QT_INSTALL", qtInstallPath)
            }

            steps {
                script {
                    id = "build_qt"
                    scriptContent = """
                            call "C:\Program Files (x86)\Microsoft Visual Studio\2022\BuildTools\VC\Auxiliary\Build\vcvarsall.bat" x64
                            cmake -S . -B build -DCMAKE_TOOLCHAIN_FILE=%QT_INSTALL%\lib\cmake\Qt6\qt.toolchain.cmake -G Ninja 
                            cmake --build build --config $buildConfig
                        """.trimIndent()
                }
                script {
                    id = "package_qt"
                    scriptContent = """
                    
                            rm -r build\${executableName}
                            mkdir build\${executableName}
                            cp build\${executableName}.exe build\${executableName}
                            %QT_INSTALL%\bin\windeployqt.exe build\${executableName}\${executableName}.exe
                            cd build
                            7z a ${executableName}.zip ${executableName}\
                """.trimIndent()
                }
            }

            requirements {
                exists("env.QT")
                exists("env.PLATFORM_WIN")
            }
        }
    }

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
                } else{
                    append(" --generate-notes")
                }
                append(" $assetsPath")
            }

            vcs{
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

    /**
     * 创建一个Git推送构建步骤
     */
    fun createGitPushStep(comment: String = "update"): BuildSteps.() -> Unit {
        return {
            script {
                this.name = "Git Push Changes"
                id = "git_push"
                this.scriptContent = """
                    git config user.email "teamcity@kvto.dev"
                    git config user.name "teamcity"
                    export GIT_SSH_COMMAND="ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no"
                    git add -A
                    git commit -m"$comment"
                    git push --force
                """.trimIndent()
            }
        }
    }

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
            steps {
                createGitPushStep()(this)
            }
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