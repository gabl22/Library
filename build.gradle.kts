plugins {
    id("java")
}

group = "me.gabl"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

defaultTasks("build", "shadowJar")

dependencies {
    implementation(group = "com.google.code.gson", name = "gson", version = "2.8.9")
    implementation("org.jetbrains:annotations:20.1.0")
    implementation("org.jetbrains:annotations:20.1.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.VERSION_17.toString()
    targetCompatibility = JavaVersion.VERSION_17.toString()
    options.encoding = "UTF-8"
    options.isIncremental = true
}

tasks.withType<Jar> {
    archiveFileName.set("library.jar")
}