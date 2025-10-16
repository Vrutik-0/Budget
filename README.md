# Budget

A simple Java command-line program for tracking and displaying budget information.

## Repository
This repository contains a single Java source file:

- `Budget.java` — main application source.

## Description
This small project demonstrates basic Java I/O and simple budget calculations. It's intended as a learning or demo project and is easy to compile and run from the command line.

## Requirements
- Java JDK (LTS recommended, see notes below)

## Build and run
From the project directory (where `Budget.java` lives):

```powershell
# Compile
javac Budget.java

# Run
java Budget
```

If the project uses packages or a build tool (Maven/Gradle), adjust the commands accordingly.

## Notes on Java runtime upgrade
If you plan to upgrade to the latest LTS Java (recommended for security and support), install the JDK and update your `JAVA_HOME` and PATH. On Windows, you can set it in PowerShell like:

```powershell
# Example (replace with your JDK path)
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-17'
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
```

This README doesn't modify the project's Java version—use your system package manager or manual installer to upgrade the JDK.

## License
Add a license file if you plan to publish this repository publicly.
