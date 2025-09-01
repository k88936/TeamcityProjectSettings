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
            password("env.S3_SECRET_KEY", "credentialsJSON:486eb30c-545f-49f3-b1b8-e4a07c95456a")
            param("env.S3_BUCKET_NAME", "software-release")
            param("env.S3_ENDPOINT", "http://10te47kl27611.vicp.fun:19000")
            param("env.AWS_REGION", "us-east-1")
            password("env.S3_ACCESS_KEY", "credentialsJSON:23688bbb-1b91-46ba-b441-09702c979512")
        }
    })
})