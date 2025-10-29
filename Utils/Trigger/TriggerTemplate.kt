package Utils.Trigger

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object TriggerTemplate {
    fun excludeCI(): BuildType.() -> Unit {
        return {
            triggers {
                vcs {
                    triggerRules += """-:comment=\[CI\]:**"""
                }
            }
        }
    }

    fun excludeUser(name: String): BuildType.() -> Unit {
        return {
            triggers {
                vcs {
                    triggerRules += """-:user=$name:**"""
                }
            }
        }
    }

    fun excludeAI(): BuildType.() -> Unit {
        return {
            excludeUser("Continue Dev")(this)
            excludeUser("Teamcity")(this)
        }
    }
}