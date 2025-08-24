package Blogs.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object Blogs_GitGithubComK88936blogsGitRefsHeadsMain : GitVcsRoot({
    id("Blogs_GitGithubComK88936blogsGitRefsHeadsMain")
    name = "git@github.com:k88936/blogs.git#refs/heads/main"
    url = "git@github.com:k88936/blogs.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})