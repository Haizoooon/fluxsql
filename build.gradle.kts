plugins {
    id("java")
    id("maven-publish")
}

group = "de.evoxy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

publishing {
    repositories {
        maven {
            name = "reposilite"
            url = uri("https://repo.evoxy.de/snapshots")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "de.evoxy"
            artifactId = "fluxsql"
            version = "1.0.0-SNAPSHOT"
            from(components["java"])
        }
    }
}

dependencies {
    implementation("com.mysql:mysql-connector-j:9.5.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}