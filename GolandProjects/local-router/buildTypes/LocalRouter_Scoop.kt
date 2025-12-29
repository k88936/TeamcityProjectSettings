package GolandProjects.LocalRouter.buildTypes

import Utils.Deploy.Version.AppManifests
import Utils.Deploy.Version.applyScoopBucketDeployment
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.FailureAction
import jetbrains.buildServer.configs.kotlin.ReuseBuilds
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger

object LocalRouter_Scoop : BuildType(
    {
        triggers {
            finishBuildTrigger {
                buildType = LocalRouter_Deploy.id?.value
                successfulOnly = true
            }
        }
        dependencies {
            snapshot(LocalRouter_Deploy) {
                reuseBuilds = ReuseBuilds.NO
                onDependencyFailure = FailureAction.CANCEL
                onDependencyCancel = FailureAction.CANCEL
                synchronizeRevisions = false
            }
        }
        val version = "v%dep.LocalRouter_Deploy.build.number%"
        val scoopManifests = AppManifests(
            "local-router", """
            {
                "version": "$version",
                "description": "local-router is a simple proxy to OpenAI compatible endpoint",
                "license": "MIT",
                "homepage": "https://github.com/k88936/local-router",
                "url": [
                    "https://github.com/k88936/local-router/releases/download/$version/local-router.exe" 
                    "https://raw.githubusercontent.com/k88936/local-router/refs/heads/main/script/install.ps1",
                    "https://raw.githubusercontent.com/k88936/local-router/refs/heads/main/script/uninstall.ps1"
                ],
                "bin": "local-router.exe",
                "depends": "nssm",
                "installer": {
                    "file": "install.ps1"
                },
                "uninstaller": {
                    "file": "uninstall.ps1"
                },
                "shortcuts": [
                    [
                        "local-router.exe",
                        "local-router"
                    ]
                ]
            } 
        """.trimIndent()
        )
        applyScoopBucketDeployment(scoopManifests)
    }
)