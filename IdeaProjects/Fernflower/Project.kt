package IdeaProjects.Fernflower

import IdeaProjects.Fernflower.buildTypes.*
import IdeaProjects.Fernflower.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.githubConnection

object Project : Project({
    id("Fernflower")
    name = "Fernflower"

    vcsRoot(Fernflower_GitGithubComK88936fernflowerGitRefsHeadsMaster)

    buildType(Fernflower_Build)
    buildType(Fernflower_Deploy)

    features {
        githubConnection {
            id = "PROJECT_EXT_2"
            displayName = "GitHub.com"
            clientId = "Ov23liYhLaN7cSZi2Yif"
            clientSecret = "credentialsJSON:d6ad8db6-9b92-4f96-9fc9-e96e4abe8585"
        }
    }
})
