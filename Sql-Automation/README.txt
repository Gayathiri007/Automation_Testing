SqlGuru Automation Testing Framework
====================================

Overview
--------
This project is a Selenium + TestNG automation framework for validating SQLGuru application workflows.
It integrates with Maven, generates ExtentReports, and supports both local (headed) and AWS/Linux (headless) execution.
The framework is CI/CD‑ready with AWS CodeBuild + CodePipeline integration.

Project Structure
-----------------
Sql-Automation/
├── pom.xml                  # Maven dependencies & plugins
├── TestNG.xml               # Test suite configuration
├── src/
│   ├── main/java            # Application code
│   └── test/java            # Test cases
│       └── testCases/       # Organized test classes
├── reports/                 # ExtentReports HTML output (ExtentReport.html)
├── buildspec.yml            # AWS CodeBuild pipeline config
└── readme.txt               # Project documentation

Prerequisites
-------------
- Java 19 (Corretto) or compatible JDK
- Maven 3.8+
- Chrome browser + matching chromedriver
- AWS CLI (for CI/CD integration)

Setup
-----
1. Clone the repository:
   git clone <URL>
   cd sqlguru-automation/Sql-Automation

2. Install dependencies:
   mvn clean install -DskipTests

3. Run tests locally (headed mode):
   mvn test -DsuiteFile=TestNG.xml

4. Run tests in headless mode:
   mvn test -Dheadless=true -DsuiteFile=TestNG.xml

Test Execution
--------------
- TestNG.xml defines the test suites.
- Tests are executed via Maven Surefire plugin.
- Headless mode is controlled by:
  System.getProperty("headless", "true")

Reporting
---------
- Reports are generated using ExtentReports.
- Output: reports/ExtentReport.html (always overwritten with the latest run).
- In CI/CD, reports are uploaded as CodePipeline artifacts.

CI/CD Integration
-----------------
- AWS CodeBuild runs tests in headless mode.
- buildspec.yml defines:
    - Install phase → Maven dependencies
    - Build phase → Run TestNG tests
    - Artifacts → Upload reports/ExtentReport.html

Reports can be downloaded from CodePipeline execution artifacts.

Best Practices
--------------
- Always overwrite ExtentReport.html to keep only the latest run.
- Use CodePipeline artifacts for secure storage.
- Add a final Deploy to S3 stage for easy browser access.

Author
------
Rajkumar