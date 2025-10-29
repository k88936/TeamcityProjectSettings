package Utils.Trigger

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object TriggerTemplate {
    private fun addVcsTriggerRule(rule: String): BuildType.() -> Unit {
        return {
            (triggers.items.find { it is VcsTrigger } as? VcsTrigger)?.apply {
                triggerRules += "\n$rule"
            } ?: run {
                triggers {
                    vcs {
                        triggerRules = rule
                    }
                }
            }
        }
    }

    fun excludeCI(): BuildType.() -> Unit {
        return addVcsTriggerRule("""-:comment=\[CI\]:**""")
    }

    fun excludeUser(name: String): BuildType.() -> Unit {
        return addVcsTriggerRule("-:user=$name:**")
    }

    fun excludeAI(): BuildType.() -> Unit {
        return {
            excludeUser("Continue Dev")(this)
            excludeUser("Teamcity")(this)
        }
    }
}