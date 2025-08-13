package Keetcoder.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object Keetcoder_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster : GitVcsRoot({
    name = "git@github.com:k88936/leetcode-editor.git#refs/heads/master"
    url = "git@github.com:k88936/leetcode-editor.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})
