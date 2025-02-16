plugins {
    id("java")
    id("application")
}

group = "ru.matthewyurkevich"

repositories {
    mavenCentral()
}

application {
    mainClass.set("ru.matthewyurkevich.Main")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
}