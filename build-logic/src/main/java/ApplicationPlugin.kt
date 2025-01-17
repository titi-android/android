
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import com.example.build_logic.configureCompose
import com.example.build_logic.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal class ApplicationPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
        }


        extensions.configure<ApplicationExtension> {
            configureKotlinAndroid(this)
            configureCompose(this)
            buildTypes {
                release {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }
        }

//        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
//        dependencies {
//            add("implementation", libs.findLibrary("junit4").get())
//        }
    }

}