plugins {
    `java-library`
    jacoco
    `maven-publish`
    signing
}

group="de.hatoka.kube"
description="Rubik's Cube Solver"
// will be defined by build
// version=

val sonatypeUsername: String? by project
val sonatypePassword: String? by project
val sonatypeRepo: Any by project

repositories {
    gradlePluginPortal()
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
        vendor.set(JvmVendorSpec.ADOPTIUM)
    }

    withJavadocJar()
    withSourcesJar()
}

jacoco {
    toolVersion = "0.8.12"
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }

    withType<JacocoReport> {
        reports {
            xml.required.set(true)
            html.required.set(true)
        }

        val jacocoTestReport by tasks
        jacocoTestReport.dependsOn("test")
    }
}

publishing {
    publications {
        create<MavenPublication>("hatokaMvn") {
            from(components["java"])

            pom {
                name.set(project.name)
                description.set(project.description)
                url.set("https:/github.com/Thomas-Bergmann/${project.name}")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("tm")
                        name.set("Thomas Bergmann")
                        email.set("tm@hatoka.de")
                    }
                }

                scm {
                    connection.set("https://github.com/Thomas-Bergmann/${project.name}.git")
                    developerConnection.set("git@github.com:Thomas-Bergmann/${project.name}.git")
                    url.set("https://github.com/Thomas-Bergmann/${project.name}")
                }
            }
        }
    }
    repositories {
        maven {
            url = uri(sonatypeRepo)
            credentials {
                username = sonatypeUsername
                password = sonatypePassword
            }
        }
    }
}

signing {
    sign(publishing.publications["hatokaMvn"])
}

val logbackVersion = "1.4.14"
val slf4jVersion = "2.0.9"
val junitVersion = "5.10.0"
val junitPlatformVersion = "1.10.2"

dependencies {
    implementation("org.slf4j:slf4j-api:${slf4jVersion}")
    runtimeOnly("ch.qos.logback:logback-classic:${logbackVersion}")
    runtimeOnly("ch.qos.logback:logback-core:${logbackVersion}")

    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter:${junitVersion}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:${junitPlatformVersion}")
}
