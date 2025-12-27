package WebstormProjects.ReactNative.Fcalender.buildTypes

import DockerProjects.DockerBuildTemplate
import WebstormProjects.ReactNative.Fcalender.vcsRoots.FcalenderVCS
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.sshAgent
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

    DockerBuildTemplate.createDockerBuild(
        imageName = "kvtodev/fcalendar:dev",
        dockerfilePath = "backend/Dockerfile",
        connection = "DOCKER_REGISTRY_CONNECTION",
    )(this)
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
        sshAgent {
            teamcitySshKey = "id_rsa"
        }
    }
    maxRunningBuilds = 1
})