## Birthday Greeting
This applications is designed to send greeting message to employees when their birthday.

## Architecture
```markdown
.
├── README.md
├── build.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── tony
    │   │           └── bday
    │   │               └── greeting
    │   │                   |   └── Application.java         (Main Class)
    │   │                   └── model                        (Custom Models)
    │   │                   └── router                       (Camel Routers)
    │   │                   └── processor                    (Custom Logic Layer)
    │   └── resources                                        (Custom Resouces)
```
### How to Build Project:
- Change the value of target.file.path in {YOUR_WORKSPACE}\bday-greeting\src\main\resources\application.properties.
```bash
E.g. my workspace is D:\\workspace
target.file.path=file://D:\\workspace\\bday-greeting\\src\\main\\resources
```

- Execute following command to build project:
```bash
#This command assembles an executable jar file under build/lib/ folder.
./gradlew clean build
```

- Execute following command to build project:
```bash
#This command assembles an executable jar file under build/lib/ folder.
./gradlew clean eclipse
```
### How to Run :
- After building a jar, you can run bday-greeting:
```bash
java -jar ./build/libs/bday-greetingt-0.1.0.jar
```
### Technical Stack 
    - Platform: Java 8
    - Container Frameworks: Spring Boot (1.5.3.RELEASE)
    - Intergration framework: Apache Camel (2.1.5.RELEASE)