package ClionProjects.Qt

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.script

fun BuildType.applyQtWindowsBuild(
    name: String = "Build",
    installPath: String = "C:\\Qt\\6.10.0\\msvc2022_64",
    buildConfig: String = "Release",
    executableName: String = "Application"
) {
    this.name = name
    artifactRules = "build/${executableName}.zip"
    params {
        text("QT_INSTALL", installPath)
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