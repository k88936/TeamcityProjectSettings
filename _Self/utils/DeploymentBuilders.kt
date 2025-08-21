package _Self.utils

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.buildSteps.powerShell
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.vcs

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
                text("env.QT_INSTALL", qtInstallPath)
            }

            steps {
                script {
                    id = "build_qt"
                    scriptContent = """
                            call "C:\Program Files (x86)\Microsoft Visual Studio\2022\BuildTools\VC\Auxiliary\Build\vcvarsall.bat" x64
                            cmake -S . -B build -DCMAKE_TOOLCHAIN_FILE=%env.QT_INSTALL%\lib\cmake\Qt6\qt.toolchain.cmake -G Ninja 
                            cmake --build build --config $buildConfig
                        """.trimIndent()
                }
                script {
                    id = "package_qt"
                    scriptContent = """
                    
                            rm -r build\${executableName}
                            mkdir build\${executableName}
                            cp build\${executableName}.exe build\${executableName}
                            %env.QT_INSTALL%\bin\windeployqt.exe build\${executableName}\${executableName}.exe
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
     * 创建一个Git推送构建步骤
     */
    fun createGitPushStep(): BuildSteps.() -> Unit {
        return {
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
        }
    }
}