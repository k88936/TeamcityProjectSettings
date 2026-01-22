package Root

import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.activeStorage
import jetbrains.buildServer.configs.kotlin.projectFeatures.dockerRegistry
import jetbrains.buildServer.configs.kotlin.projectFeatures.githubAppConnection
import jetbrains.buildServer.configs.kotlin.projectFeatures.s3CompatibleStorage

object Project : Project({
    description = "Contains all other projects"

    features {
    }

    cleanup {
        baseRule {
            preventDependencyCleanup = false
        }
    }

    subProject(IdeaProjects.Project)
    subProject(ClionProjects.Project)
    subProject(UnityProjects.Project)
    subProject(DockerProjects.Project)
    subProject(WebstormProjects.Project)
    subProject(RustroverProjects.Project)
    subProject(GolandProjects.Project)
    subProject(ScoopBucket.Project)

    params {
        password("KVTO_RUSTFS_ACCESS_KEY", "credentialsJSON:5fc336a3-cb50-4cf1-aabd-7b2e77165b02")
        password("KVTO_RUSTFS_SECRET_KEY", "credentialsJSON:2363a321-c78d-4a04-ad69-40c1fec750d5")
        password("KVTO_GH_TOKEN", "credentialsJSON:04d96fb0-dbf8-457b-be29-2327ab11dd68")
    }

    features {
        dockerRegistry {
            id = "DOCKER_REGISTRY_CONNECTION"
            name = "Docker Registry"
            userName = "kvtodev"
            password = "credentialsJSON:a43b4956-2b9e-457c-bf3c-6e04db2adc38"
        }
        githubAppConnection {
            id = "PROJECT_EXT_4"
            displayName = "k88936/TeamCity"
            appId = "2210972"
            clientId = "Iv23li1V4XlIXvtqlwq1"
            clientSecret = "credentialsJSON:b39a599e-3ddc-4b9c-b560-34f11c98a5f9"
            privateKey = "credentialsJSON:db545cdc-cf1c-4643-8881-46d084fdd7ab"
            webhookSecret = "credentialsJSON:0f3bb38b-c2c6-4fec-a8ca-6287f91611a8"
            ownerUrl = "https://github.com/k88936"
            useUniqueCallback = true
        }
        s3CompatibleStorage {
            id = "PROJECT_EXT_3"
            accessKeyID = "PrunStQTawifpVN8A1Mc"
            accessKey = "credentialsJSON:535a3e4e-5376-4808-8374-fc82c559bea5"
            endpoint = "https://rustfs.k88936.top"
            bucketName = "teamcity-artifact"
            forceVirtualHostAddressing = true
        }
        activeStorage {
            id = "PROJECT_EXT_5"
            activeStorageID = "PROJECT_EXT_3"
        }
    }
})