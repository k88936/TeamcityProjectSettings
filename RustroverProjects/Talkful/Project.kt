package RustroverProjects.Talkful

import RustroverProjects.Talkful.buildTypes.Talkful_Build
import RustroverProjects.Talkful.buildTypes.Talkful_Deploy
import RustroverProjects.Talkful.vcsRoots.Talkful_GitGithubComK88936talkfulGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("Talkful")
    name = "Talkful"

    vcsRoot(Talkful_GitGithubComK88936talkfulGitRefsHeadsMain)

    buildType(Talkful_Build)
    buildType(Talkful_Deploy)
})

