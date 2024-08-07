rootProject.name = "user"

include("util", "domain", "application", "adapter", "api")

pluginManagement {
    repositories {
        gradlePluginPortal()
    }

    plugins {
        id("org.springframework.boot") version "3.1.3"
        id("io.spring.dependency-management") version "1.1.3"
        id("org.jetbrains.kotlin.jvm") version "1.8.22"
        id("org.jetbrains.kotlin.plugin.spring") version "1.8.22"
        id("org.jetbrains.kotlin.plugin.allopen") version "1.9.10"
        kotlin("plugin.noarg") version "1.8.21"
    }
}
