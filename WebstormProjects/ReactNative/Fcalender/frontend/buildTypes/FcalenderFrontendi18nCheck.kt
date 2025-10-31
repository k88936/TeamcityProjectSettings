package WebstormProjects.ReactNative.Fcalender.frontend.buildTypes

import Utils.AI.ContinueAITemplate
import Utils.Trigger.TriggerTemplate
import Utils.Version.GithubTemplate
import WebstormProjects.ReactNative.Fcalender.frontend.vcsRoots.FcalenderFrontendVCS
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object FcalenderFrontendi18nCheck : BuildType({
    name = "i18nCheck"


    vcs {
        root(FcalenderFrontendVCS)
    }
    triggers {
        vcs {
            branchFilter = """
                +:<default>
                +:frontend*
                +:frontend
            """.trimIndent()
        }
    }


    TriggerTemplate.excludeCI()(this)
    TriggerTemplate.excludeAI()(this)
    features {
        perfmon {
        }
    }
    failureConditions {
        executionTimeoutMin = 10
    }

    ContinueAITemplate.createStep(
        """
        Task: check if the new commit fully support i18n(zh and en, configured in i18n/ HtmlStyles.index.ts)
        To work more efficiently, you should: firstly check the new commit message(or diff if needed) to see if it is about frontend UI, if not, end this task.
        Try to avoid scan the whole workspace as possible.
        If there is something to improve, patch it and create a new commit with proper message.
        besides, you should check for:
        ## i18n Integration Guide

        ### Page Titles (Stack Headers)

        - **In `app/_layout.tsx`:**  
          Use translation keys for titles:
          ```tsx
            HtmlStyles.title: t('auth.login.headerTitle')
            HtmlStyles.title: t('auth.signup.headerTitle')
          ```

        - **In `i18n/index.ts`:**  
          Define clear, stable keys per screen under appropriate namespaces:
          ```ts
          auth: {
            login: { headerTitle: 'Log In' },
            signup: { headerTitle: 'Sign Up' }
          }
          ```

        ---

        ### Error Messages

        - **Throw errors using i18n keys as messages:**  
          

          ```ts
          ```

          > **Note:** When adding new errors, ensure all `throw` statements use i18n keys.

        - **Add keys to `i18n/index.ts` for all supported languages:**  
          Include entries under the `errors` namespace for both English and Chinese:

          ```ts
          // English
          errors: {
            PasswordNotMatchError: 'Passwords do not match',
            // ... other errors
          }

          // Chinese (zh)
          errors: {
            PasswordNotMatchError: '两次输入的密码不一致',
            // ... other errors
          }
          especially pay attention to the validation related and api related Error
          ```
    """.trimIndent(),
        workdir = "frontend"
    )(this)
    GithubTemplate.createPRStep(
        "teamcity-i18n-support-check",
        "check and improve i18n support",
        "improve frontend i18n support"
    )(this)

})
