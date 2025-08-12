package LeetcodeEditor.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger

object LeetcodeEditor_Deploy : BuildType({
    name = "Deploy"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    params {
        password("env.GITHUB_TOKEN", "credentialsJSON:9b5eb1f1-fa5c-46a2-ad86-23ba0f76364d")
    }

    vcs {
        root(LeetcodeEditor.vcsRoots.LeetcodeEditor_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster)
    }

    steps {
        script {
            id = "simpleRunner"
            scriptContent = "gh release create v%build.number% --generate-notes ghrelease/*"
        }
    }

    triggers {
        finishBuildTrigger {
            buildType = "${LeetcodeEditor_Build.id}"
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(LeetcodeEditor_Build) {
            buildRule = lastSuccessful()
            cleanDestination = true
            artifactRules = "*.zip=>ghrelease/"
        }
    }
})
