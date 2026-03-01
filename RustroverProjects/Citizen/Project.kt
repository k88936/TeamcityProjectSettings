package RustroverProjects.Citizen

import RustroverProjects.Citizen.buildTypes.Citizen_Build
import RustroverProjects.Citizen.buildTypes.Citizen_Deploy
import RustroverProjects.Citizen.vcsRoots.Citizen_GitGithubComK88936citizenGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("Citizen")
    name = "Citizen"

    vcsRoot(Citizen_GitGithubComK88936citizenGitRefsHeadsMain)

    buildType(Citizen_Build)
    buildType(Citizen_Deploy)
})
