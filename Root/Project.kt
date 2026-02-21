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
        password("KVTO_RUSTFS_ACCESS_KEY", "credentialsJSON:dbd9d18e-531c-4b59-8f18-85ee3dbf0757")
        password("KVTO_RUSTFS_SECRET_KEY", "credentialsJSON:fe76ba66-ec2d-443d-bda6-ade2fa15191f")
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
            accessKeyID = "SHd9FvJ8Gcr1s3eh6bCU"
            accessKey = "credentialsJSON:fe76ba66-ec2d-443d-bda6-ade2fa15191f"
            endpoint = "https://rustfs.k88936.top"
            bucketName = "teamcity-artifact"
            forceVirtualHostAddressing = true
            param("aws.region.name", "")
            param("aws.use.default.credential.provider.chain", "")
            param("storage.s3.forceVirtualHostAddressing", "")
        }
        activeStorage {
            id = "PROJECT_EXT_5"
            activeStorageID = "PROJECT_EXT_3"
        }
    }
})