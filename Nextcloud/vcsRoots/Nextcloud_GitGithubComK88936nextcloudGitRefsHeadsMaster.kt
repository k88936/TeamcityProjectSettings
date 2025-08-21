package Nextcloud.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object Nextcloud_GitGithubComK88936nextcloudGitRefsHeadsMaster : GitVcsRoot({
    id("Nextcloud_GitGithubComK88936nextcloudGitRefsHeadsMaster")
    name = "git@github.com:k88936/nextcloud.git#refs/heads/master"
    url = "git@github.com:k88936/nextcloud.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})