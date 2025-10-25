package WebstormProjects.GithubPages

import jetbrains.buildServer.configs.kotlin.Project

object Projects : Project({
    name = "GithubPages"
    id("GithubPages")
    subProject(GithubPageTemplate("git@github.com:k88936/rss-reader", "npm run fetch-feeds"))
    subProject(GithubPageTemplate("git@github.com:k88936/k88936.github.io.git"))
    subProject(GithubPageTemplate("git@github.com:k88936/blogs.git"))
    subProject(GithubPageTemplate("git@github.com:k88936/source-of.git") {
        it.params {
            password("env.S3_ACCESS_KEY", "credentialsJSON:1b2416b2-6e73-4b71-84f0-8a658310e714")
            password("env.S3_SECRET_KEY", "credentialsJSON:0344c592-9a94-405d-a1d0-7a6dc6ff5125")
            param("env.S3_BUCKET_NAME", "software-release")
            param("env.S3_ENDPOINT", "https://rustfs.k88936.top")
            param("env.AWS_REGION", "us-east-1")
        }
    })
})