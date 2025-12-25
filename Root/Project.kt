package Root

import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.githubAppConnection

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

    features {
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
    }
})