package RssReader

import Blogs.buildTypes.*
import Blogs.vcsRoots.*
import RssReader.buildTypes.RssReader_Build
import RssReader.vcsRoots.RssReader_GitGithubComK88936blogsGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("RssReader")
    name = "RssReader"

    vcsRoot(RssReader_GitGithubComK88936blogsGitRefsHeadsMain)

    buildType(RssReader_Build)
})