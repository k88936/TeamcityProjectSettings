package RssReader.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object RssReader_GitGithubComK88936blogsGitRefsHeadsMain : GitVcsRoot({
    id("RssReaders_GitGithubComK88936blogsGitRefsHeadsMain")
    name = "git@github.com:k88936/rss-reader.git#refs/heads/main"
    url = "git@github.com:k88936/rss-reader.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})