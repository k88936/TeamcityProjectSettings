package AndroidStudioProjects.NextcloudTv.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object NextcloudTv_GitGithubComK88936nextcloudTvGitRefsHeadsMain : GitVcsRoot({
    name = "git@github.com:k88936/nextcloud-tv.git#refs/heads/main"
    url = "git@github.com:k88936/nextcloud-tv.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})
