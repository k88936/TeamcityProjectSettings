package RustroverProjects.Citizen.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.FailureAction
import jetbrains.buildServer.configs.kotlin.ReuseBuilds
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import utils.deploy.version.AppManifests
import utils.deploy.version.applyScoopBucketDeployment

object Citizen_Scoop : BuildType(
    {
        triggers {
            finishBuildTrigger {
                buildType = Citizen_Deploy.id?.value
                successfulOnly = true
            }
        }
        dependencies {
            snapshot(Citizen_Deploy) {
                reuseBuilds = ReuseBuilds.NO
                onDependencyFailure = FailureAction.CANCEL
                onDependencyCancel = FailureAction.CANCEL
                synchronizeRevisions = false
            }
        }
        val version = "build-%dep.Citizen_Deploy.build.number%"
        val scoopManifests = AppManifests(
            "citizen", """
           {
                "version": "$version",
                "description": "citizen command line tool.",
                "license": "MIT",
                "homepage": "https://github.com/k88936/citizen",
                "url": "https://github.com/k88936/citizen/releases/download/$version/citizen.exe",
                "bin": "citizen.exe",
                "shortcuts": [
                    [
                        "citizen.exe",
                        "citizen"
                    ]
                ]
           }
        """.trimIndent()
        )
        applyScoopBucketDeployment(scoopManifests)
    }
)

