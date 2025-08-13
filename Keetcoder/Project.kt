package Keetcoder

import Keetcoder.buildTypes.*
import Keetcoder.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("Keetcoder")
    name = "Keetcoder"

    vcsRoot(Keetcoder_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster)

    buildType(Keetcoder_Deploy)
    buildType(Keetcoder_Build)
})
