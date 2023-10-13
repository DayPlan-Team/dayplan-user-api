import org.springframework.boot.gradle.tasks.bundling.BootJar
import com.google.protobuf.gradle.*

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = true
jar.enabled = true

plugins {
    id("com.google.protobuf") version "0.9.4"
    kotlin("plugin.jpa") version "1.8.22"
}

dependencies {

    /* API */
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-java8:2.9.0")

    /* GRPC */
    implementation("io.grpc:grpc-kotlin-stub:1.3.0")
    implementation("com.google.protobuf:protobuf-java:3.24.3")
    implementation("io.grpc:grpc-netty-shaded:1.58.0")
    implementation("io.grpc:grpc-protobuf:1.58.0")
    implementation("net.devh:grpc-server-spring-boot-starter:2.15.0.RELEASE")


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

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.24.3"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.58.0"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.4.0:jdk8@jar"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                add(GenerateProtoTask.PluginOptions("grpc"))
                add(GenerateProtoTask.PluginOptions("grpckt"))
            }
        }
    }
}

sourceSets {
    main {
        proto {
            srcDir("user/api/src/main/proto")
        }
        java {
            srcDirs("user/api/build/generated/source/proto/main/grpc")
            srcDirs("user/api/build/generated/source/proto/main/java")
        }
    }
}
