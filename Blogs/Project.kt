package Blogs

import Blogs.buildTypes.*
import Blogs.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("Blogs")
    name = "Blogs"

    vcsRoot(Blogs_GitGithubComK88936blogsGitRefsHeadsMain)

    buildType(Blogs_Build)
})