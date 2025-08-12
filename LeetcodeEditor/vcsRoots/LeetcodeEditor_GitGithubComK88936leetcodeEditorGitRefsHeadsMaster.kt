package LeetcodeEditor.vcsRoots

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object LeetcodeEditor_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster : GitVcsRoot({
    name = "git@github.com:k88936/leetcode-editor.git#refs/heads/master"
    url = "git@github.com:k88936/leetcode-editor.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "k99836 github"
    }
})
