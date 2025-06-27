plugins {
    java
    war
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Spring MVC
    implementation("org.springframework:spring-context:6.1.5")
    implementation("org.springframework:spring-core:6.1.5")
    implementation("org.springframework:spring-web:6.1.5")
    implementation("org.springframework:spring-webmvc:6.1.5")
    
    // Servlet API
    compileOnly("javax.servlet:javax.servlet-api:4.0.1")
    compileOnly("javax.servlet.jsp:javax.servlet.jsp-api:2.3.3")
    
    // JSON processing
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    
    // Swagger/OpenAPI
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    
    // AOP
    implementation("org.aspectj:aspectjweaver:1.9.19")
    implementation("org.springframework:spring-aop:6.1.5")
    
    // CSV processing
    implementation("com.opencsv:opencsv:5.8")
    
    // Logging
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.4.11")

    // Testing
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework:spring-test:6.1.5")
    testImplementation("org.mockito:mockito-core:5.7.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.7.0")
}

java {
    sourceSets {
        getByName("main") {
            java.srcDirs("src/main/java")
            resources.srcDirs("src/main/resources")
        }
        getByName("test") {
            java.srcDirs("src/test/java")
            resources.srcDirs("src/test/resources")
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
