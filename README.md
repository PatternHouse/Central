# Central
This repository contains all the additional tools and scripts used in openAOD

## Directory Wise Breakdown
### ``.semaphore/`` - Semaphore Deployment Scripts
Builds and auto deploys the PatternHouse websites to ``openAOD/patternhouse-o-o``. The build job is scheduled to auto deploy on every Sunday on 08:30 AM GMT+0.
### ``./Assets/`` - Shared Static Website Assets
  * ``./Assets/img/`` - Shared Static Images (shared between patternhouse-main and patternhouse-webui)
  * ``./Assets/patternhouse-main/`` - CSS and JS scripts relating to patternhouse-main (the main website frontend of PatternHouse)
  * ``./Assets/patternhouse-webui/`` - CSS and JS scripts relating to patternhouse-webui (PatternHouse Portal)
  * ``./Assets/patterns/`` - Images of all the patterns in PatternHouse
### ``./Patches/`` - Optional Patches(mostly for development marks)
### ``./Scripts/`` - Build and Debug scripts for PatternHouse Main/WebUI
All builds are done is a separate build environment (``./Build/``). The static website assets are synced automatically to respective locations. The major scripts are:
  * ``./Scripts/build.sh`` - Clean build of patternhouse-main and patternhouse-webui (will clean out debugging log files and unnecessary binaries)
  * ``./Scripts/debug.sh`` - Clean build of patternhouse-main and patternhouse-webui (will persist debugging log files and binaries)
### ``./Sources/`` - Static Webpages

### ``./Templates/`` - Template website frontends for FIRE
  * ``./Templates/PortalDisplay`` - Main template that will hold single page display of all the patterns for patternhouse-webui
  * ``./Templates/SourceDisplay`` - Main template that will hold source code display for a given pattern for patternhouse-webui
### ``./TestSuite/`` - Testing scripts for testing PatternHouse source code
  * ``./TestSuite/test.sh``- The main testing script
  
  #### Usage:
  ```bash
  bash test.sh {langauge}
  ```
  where languge can be any one of ``{c, cpp, cs, java, python, js}``. The script must be run in the root directory of the relavant source directory.
### ``./Tools/Fire/`` - The main build script for patternhouse-ui (java, maven)
  #### Compiling:
  ```bash
  mvn package
  ```
  #### Usage:
  ```bash
  java -jar fire.jar
  ```
  in the relevant build directory.
