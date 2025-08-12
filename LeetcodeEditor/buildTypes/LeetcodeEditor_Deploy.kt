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
    }

    vcs {
        root(LeetcodeEditor.vcsRoots.LeetcodeEditor_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster)
    }

    steps {
        script {
            id = "simpleRunner"
            scriptContent = "gh release create build-%build.number% --generate-notes _deploy/*"
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
            artifactRules = "*.zip=>_deploy/"
        }
    }
})
