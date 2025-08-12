package Shotmd.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.powerShell
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Shotmd_Build : BuildType({
    name = "Build"

    artifactRules = "build/Shotmd.zip"

    vcs {
        root(Shotmd.vcsRoots.Shotmd_GitGithubComK88936ShotmdGitRefsHeadsMaster)
    }

    steps {
        powerShell {
            id = "jetbrains_powershell"
            scriptMode = script {
                content = """
                    cmake -S . -B build -DCMAKE_TOOLCHAIN_FILE=%env.QT_WIN_INSTALL%/lib/cmake/Qt6/qt.toolchain.cmake -G Ninja 
                    cmake --build build --config Release
                    mkdir build/Shotmd
                    cp build/Shotmd.exe build/Shotmd
                    %env.QT_WIN_INSTALL%/bin/windeployqt.exe build/Shotmd/Shotmd.exe
                    cd build
                    7z a Shotmd.zip Shotmd/
                """.trimIndent()
            }
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }

    requirements {
        exists("env.QT_WIN_INSTALL")
    }
})
