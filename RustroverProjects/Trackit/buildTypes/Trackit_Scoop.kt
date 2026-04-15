package RustroverProjects.Trackit.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.FailureAction
import jetbrains.buildServer.configs.kotlin.ReuseBuilds
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import utils.deploy.version.AppManifests
import utils.deploy.version.applyScoopBucketDeployment

object Trackit_Scoop : BuildType(
    {
        triggers {
            finishBuildTrigger {
                buildType = Trackit_Deploy.id?.value
                successfulOnly = true
            }
        }
        dependencies {
            snapshot(Trackit_Deploy) {
                reuseBuilds = ReuseBuilds.NO
                onDependencyFailure = FailureAction.CANCEL
                onDependencyCancel = FailureAction.CANCEL
                synchronizeRevisions = false
            }
        }
        val version = "build-%dep.Trackit_Deploy.build.number%"
        val scoopManifests = AppManifests(
            "trackit", """
           {
                "version": "$version",
                "description": "trackit command line tool.",
                "license": "MIT",
                "homepage": "https://github.com/k88936/trackit",
                "url": "https://github.com/k88936/trackit/releases/download/$version/trackit.exe",
                "bin": "trackit.exe",
                "shortcuts": [
                    [
                        "trackit.exe",
                        "trackit"
                    ]
                ]
           }
        """.trimIndent()
        )
        applyScoopBucketDeployment(scoopManifests)
    }
)

