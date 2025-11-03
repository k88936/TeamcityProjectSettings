package RustroverProjects.Rustdesk.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType

object Rustdesk_Deploy : BuildType({
    id("Rustdesk_Deploy")
    name = "Deploy"
    dependencies{
    }
})