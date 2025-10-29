package WebstormProjects.ReactNative.Fcalender.frontend.buildTypes

import Utils.AI.ContinueAITemplate
import Utils.Trigger.TriggerTemplate
import Utils.Version.GithubTemplate
import WebstormProjects.ReactNative.Fcalender.vcsRoots.FcalenderMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object FcalenderFrontendi18nCheck : BuildType({
    name = "i18nCheck"


    vcs {
        root(FcalenderMain)
    }
    triggers {
        vcs {
            branchFilter = """
                +:frontend*
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
Check if the new commit fully support i18n(zh and en).
To work more efficiently, you should: firstly check the new commit message(or diff if needed) to see if it is about frontend UI, if not, end this task.
Try to avoid scan the whole workspace as possible.
If there is something to improve, patch it and create a new commit with proper message.

## i18n Integration Guide

### Page Titles (Stack Headers)

- **In `app/_layout.tsx`:**  
  Use translation keys for titles:
  ```tsx
  title: t('auth.login.headerTitle')
  title: t('auth.signup.headerTitle')
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
  The error message string must be a translation key, not plain text.

  ```ts
  // Incorrect
  throw new PasswordNotMatchError("the two password inputted not match")

  // Correct
  throw new PasswordNotMatchError("errors.PasswordNotMatchError")
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
  ```

    """.trimMargin().replace('"',' '),
        workdir = "frontend"
    )(this)
    GithubTemplate.createPRStep("i18nCheck", "check and fix i18n", "improve i18n support")(this)
})
