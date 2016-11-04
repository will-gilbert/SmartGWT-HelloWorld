# Sample UI app using Cucumber, Page Objects, Groovy & Gradle

This build requires JDK 1.8

Open a command window and run:

    ./gradlew usage
    ./gradlew test
    ./gradlew jetty
    ./gradlew pageObjectsTest
    ./gradlew cucumber
    ./gradlew clean
    
or for Windows:

    gradle.bat usage
    gradle.bat test
    gradle.bat jetty
    gradle.bat pageObjectsTest
    gradle.bat cucumber
    gradle.bat clean

If already have Gradle installed use:

    gradle usage
    gradle test
    gradle jetty
    gradle pageObjectsTest
    gradle cucumber
    gradle clean

This runs Cucumber as separate Gradle task using the Cucumber-JVM for Gradle implementation.

The HTML output will found in:

	build/reports/tests/cucumber/
	build/reports/tests/pageObjectTest/
	build/reports/tests/test/


**NB**: If you have **Gradle** installed you can ignore or delete the files `gradlew` and `gradlew.bat` and the folder `gradle`.