package Sites

import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.ui.addOption

object Projects : Project({
    name = "Sites"
    id("Sites")
    description = "contain all websites"
    subProject(GithubPageTemplate("git@github.com:k88936/rss-reader", "npm run fetch-feeds"))
    subProject(GithubPageTemplate("git@github.com:k88936/k88936.github.io.git"))
    subProject(GithubPageTemplate("git@github.com:k88936/source-of.git") {
        it.params {
            password("env.S3_SECRET_KEY", "credentialsJSON:6d9f4af1-89b3-41a3-9bcc-fde1bdd8e7f9")
            param("env.S3_BUCKET_NAME", "software-release")
            param("env.S3_ENDPOINT", "http://10te47kl27611.vicp.fun:19000")
            param("env.S3_AWS_REGION", "us-east-1")
            password("env.S3_ACCESS_KEY", "credentialsJSON:e2bf46ef-fcae-472d-a934-117a88db5241")
        }
    })
})