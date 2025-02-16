plugins {
    id("busSchedule.android.compose.library")
}

android {
    namespace = "com.busschedule.widget"
}

dependencies {
    implementation("androidx.glance:glance-appwidget:1.1.1")
    implementation( "androidx.glance:glance-material:1.1.1" )
    implementation( "androidx.glance:glance-material3:1.1.1" )

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}