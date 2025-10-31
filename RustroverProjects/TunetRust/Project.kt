package RustroverProjects.TunetRust

import RustroverProjects.TunetRust.buildTypes.TunetRust_Build
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("TunetRust")
    name = "Tunet Rust"

    vcsRoot(RustroverProjects.TunetRust.vcsRoots.TunetRust_GitGithubComK88936tunetRustGitRefsHeadsMain)
    buildType(TunetRust_Build)
    buildType(RustroverProjects.TunetRust.buildTypes.TunetRust_Deploy)
})
