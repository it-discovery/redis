plugins {
    java
    eclipse
}

allprojects {
    group = "it.discovery"
}

subprojects {
    apply(plugin = "java")

    java.sourceCompatibility = JavaVersion.VERSION_21
    java.targetCompatibility = JavaVersion.VERSION_21

    repositories {
        mavenCentral()
    }

    dependencies {
        val springBootVersion: String by project
        implementation(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
        testImplementation("org.springframework.boot:spring-boot-starter-test")

        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok:1.18.30")
        implementation(platform("org.testcontainers:testcontainers-bom:1.19.5"))
    }
    tasks.test {
        useJUnitPlatform()
    }
}

