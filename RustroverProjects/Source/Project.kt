package RustroverProjects.Source

import RustroverProjects.Source.buildTypes.Source_Build
import RustroverProjects.Source.buildTypes.Source_Build_Android
import RustroverProjects.Source.buildTypes.Source_Build_Linux
import RustroverProjects.Source.buildTypes.Source_Build_Windows
import RustroverProjects.Source.buildTypes.Source_Deploy
import RustroverProjects.Source.vcsRoots.Source_GitGithubComK88936sourceGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("Source")
    name = "Source"

    vcsRoot(Source_GitGithubComK88936sourceGitRefsHeadsMain)

    buildType(Source_Build)
    buildType(Source_Build_Linux)
    buildType(Source_Build_Windows)
    buildType(Source_Build_Android)
    buildType(Source_Deploy)
})
