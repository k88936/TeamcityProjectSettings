package LeetcodeEditor

import LeetcodeEditor.buildTypes.*
import LeetcodeEditor.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("LeetcodeEditor")
    name = "Leetcode Editor"

    vcsRoot(LeetcodeEditor_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster)

    buildType(LeetcodeEditor_Deploy)
    buildType(LeetcodeEditor_Build)
})
