package RustroverProjects.Talkful.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object Talkful_GitGithubComK88936talkfulGitRefsHeadsMain : GitVcsRoot({
    name = "git@github.com:k88936/talkful.git#refs/heads/main"
    url = "git@github.com:k88936/talkful.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})

