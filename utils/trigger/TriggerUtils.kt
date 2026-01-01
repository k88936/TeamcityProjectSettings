package utils.trigger

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.triggers.vcs

private fun BuildType.addVcsTriggerRule(rule: String) {
    (triggers.items.find { it is VcsTrigger } as? VcsTrigger)?.let { vcsTrigger ->
        vcsTrigger.triggerRules = when {
            vcsTrigger.triggerRules.isNullOrBlank() -> rule
            else -> "${vcsTrigger.triggerRules}\n$rule"
        }
    } ?: triggers {
        vcs {
            triggerRules = rule
        }
    }
}

fun BuildType.excludeCI() {
    addVcsTriggerRule("-:comment=\\[CI\\]:**")
}

fun BuildType.excludeUser(name: String) {
    addVcsTriggerRule("-:user=$name:**")
}

fun BuildType.excludeAI() {
    excludeUser("Continue Dev")
    excludeUser("Teamcity")
}