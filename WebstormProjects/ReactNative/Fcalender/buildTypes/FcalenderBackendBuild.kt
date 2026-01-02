package WebstormProjects.ReactNative.Fcalender.buildTypes

import WebstormProjects.ReactNative.Fcalender.vcsRoots.FcalenderVCS
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.dockerRegistryConnections
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildFeatures.sshAgent
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.buildSteps.sshExec
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object FcalenderBackendBuild : BuildType({
    id("FcalenderBackendBuild")
    name = "FcalenderBackendBuild"
    vcs {
        root(FcalenderVCS)
    }

    triggers {
        vcs {
            branchFilter = """
               +:<default>
               +:backend-playground
               +:dev
           """.trimIndent()
        }
    }
//    excludeCI()
//    excludeAI()

    val imageName = "kvtodev/fcalendar:main"
    val dockerfilePath = "backend/Dockerfile"
    steps {
        dockerCommand {
            id = "build"
            commandType = build {
                source = file {
                    path = dockerfilePath
                }
                namesAndTags = imageName
                commandArgs = "--progress=plain"
            }
        }
        dockerCommand {
            id = "push"
            commandType = push {
                namesAndTags = imageName
                removeImageAfterPush = false
            }
        }
    }
    steps {

        sshExec {
            id = "deploy_dev"
            commands = """
               cd dev
               sudo docker-compose pull
               sudo docker-compose down
               sudo docker-compose up -d
            """.trimIndent()
            targetUrl = "fcalendar.k88936.top"
            authMethod = sshAgent {
                username = "ubuntu"
            }
        }
    }
    features {
        perfmon {}
        dockerRegistryConnections {
            loginToRegistry = on {
                dockerRegistryId = "DOCKER_REGISTRY_CONNECTION"
            }
        }
    }
    features {
        sshAgent {
            teamcitySshKey = "id_rsa"
        }
    }
    maxRunningBuilds = 1
})