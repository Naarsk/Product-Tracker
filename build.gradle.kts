plugins {
    id("com.android.application") version "8.7.2" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    id("com.google.devtools.ksp") version "2.0.0-1.0.21" apply false
}

allprojects {
    repositories {
        google() // Add this line
        mavenCentral()
        // ... other repositories if needed
    }
}