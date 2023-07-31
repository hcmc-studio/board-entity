plugins {
    kotlin("jvm") version "1.9.0"
}

group = "studio.hcmc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":data-domain"))
    implementation(project(":data-transfer-object"))
    implementation(project(":data-value-object"))
    implementation(project(":exposed-table"))
    implementation(project(":kotlin-protocol-extension"))

    implementation("org.jetbrains.kotlinx:kotlinx-datetime-jvm:0.4.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.41.1")
}

kotlin {
    jvmToolchain(17)
}