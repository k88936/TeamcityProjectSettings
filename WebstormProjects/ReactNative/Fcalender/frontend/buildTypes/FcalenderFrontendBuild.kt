package WebstormProjects.ReactNative.Fcalender.frontend.buildTypes

import Utils.Deploy.GithubReleaseDeployTemplate.createGithubReleaseDeployment
import Utils.Deploy.SourceOfDeployTemplate
import Utils.Trigger.TriggerTemplate
import WebstormProjects.ReactNative.Fcalender.frontend.vcsRoots.FcalenderFrontendVCS
import WebstormProjects.ReactNative.ReactNativeBuildTemplate
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.notifications
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.nodeJS
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object FcalenderFrontendBuild : BuildType({
    name = "Build"

    artifactRules = "frontend/android/app/build/outputs/apk/release/app-release.apk"

    vcs {
        root(FcalenderFrontendVCS)
    }


    triggers {
        vcs {
            branchFilter = """
                +:<default>
                +:frontend*
                +:wdy
                +:shq
            """.trimIndent()
        }
    }
    TriggerTemplate.excludeCI()(this)
    TriggerTemplate.excludeAI()(this)

    features {
        perfmon {}
        notifications {
            notifierSettings = emailNotifier {
                email = "k88936@qq.com"
            }
            buildFailed = true
        }
    }
    steps {
        nodeJS {
            id = "jest"
            shellScript = """
                source /etc/profile
                cd frontend
                npm ci
                npm run test
            """.trimIndent()
            dockerImage = "kvtodev/ci-containers:js"
            dockerPull = true
        }
    }
    ReactNativeBuildTemplate.createReactNativeAndroidBuild("frontend")(this)

    SourceOfDeployTemplate.createSourceOfDeployment(
        name = "Fcalender",
        assets = artifactRules
    )(this)
    SourceOfDeployTemplate.createSourceOfDeployment(
        name = "Fcalender",
        tagPattern = "latest",
        assets = artifactRules
    )(this)
    createGithubReleaseDeployment(
        tagPattern = "%teamcity.build.branch%-build-%build.number%",
        assetsPath = artifactRules,
    )(this)
})
