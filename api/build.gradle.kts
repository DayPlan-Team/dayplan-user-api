import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = true
jar.enabled = true

plugins {
    kotlin("plugin.jpa") version "1.8.22"
}

dependencies {

    /* API */
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-java8:2.9.0")

    testImplementation("com.ninja-squad:springmockk:4.0.2")

    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("com.google.api-client:google-api-client:2.2.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.ninja-squad:springmockk:4.0.2")

    implementation(project(":util"))
    implementation(project(":domain"))
    implementation(project(":adapter"))
    implementation(project(":application"))
}
