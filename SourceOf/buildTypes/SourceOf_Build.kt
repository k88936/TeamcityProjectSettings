package SourceOf.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.exec
import jetbrains.buildServer.configs.kotlin.buildSteps.nodeJS
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object SourceOf_Build : BuildType({
    name = "Build"

    artifactRules = "dist => /"

    params {
        password("env.SECRET_KEY", "credentialsJSON:6d9f4af1-89b3-41a3-9bcc-fde1bdd8e7f9")
        param("env.BUCKET_NAME", "software-release")
        param("env.S3_ENDPOINT", "http://10te47kl27611.vicp.fun:19000")
        param("env.AWS_REGION", "us-east-1")
        password("env.ACCESS_KEY", "credentialsJSON:e2bf46ef-fcae-472d-a934-117a88db5241")
    }

    vcs {
        root(SourceOf.vcsRoots.SourceOf_GitGithubComK88936sourceOfGitRefsHeadsMain)
    }

    steps {
        exec {
            id = "simpleRunner"
            enabled = false
            path = "npm"
            arguments = "run build"
        }
        nodeJS {
            id = "nodejs_runner"
            shellScript = """
                npm install
                npm run build
            """.trimIndent()
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }

    requirements {
        exists("env.JS")
    }
})
