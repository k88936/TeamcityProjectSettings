package ClionProjects.Qt.shotmd.buildTypes

import ClionProjects.Qt.Shotmd.buildTypes.Shotmd_Deploy
import Utils.Deploy.Version.AppManifests
import Utils.Deploy.Version.ScoopBucketDeployTemplate
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.FailureAction
import jetbrains.buildServer.configs.kotlin.ReuseBuilds
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger

object Shotmd_Scoop : BuildType(
    {
        triggers {
            finishBuildTrigger {
                buildType = Shotmd_Deploy.id?.value
                successfulOnly = true
            }
        }
        dependencies {
            snapshot(Shotmd_Deploy) {
                reuseBuilds = ReuseBuilds.NO
                onDependencyFailure = FailureAction.CANCEL
                onDependencyCancel = FailureAction.CANCEL
                synchronizeRevisions = false
            }
        }
        val version = "build-%dep.Shotmd_Deploy.build.number%"
        val scoopManifests = AppManifests(
            "shotmd", """
           {
                "version": "$version",
                "description": "shotmd is a small and handy tool to screenshot and convert to base64 code in order to insert image to markdown.",
                "license": "MIT",
                "homepage": "https://github.com/k88936/shotmd",
                "url": "https://github.com/k88936/shotmd/releases/download/$version/shotmd.zip",
                "extract_dir": "shotmd",
                "bin": "shotmd.exe",
                "shortcuts": [
                    [
                        "shotmd.exe",
                        "shotmd"
                    ]
                ],
            } 
        """.trimIndent()
        )
        ScoopBucketDeployTemplate.createDeployTemplate(scoopManifests)(this)
    }
)