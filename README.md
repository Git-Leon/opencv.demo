# OpenCV for Java; Face Detection
* The of intent of this project is to create a way to easily interact with `OpenCV` on any platform. 

## Overview
* **Objective** - To implement an `OpenCV`-wrapper-library
* **Purpose** - to establish a small importable `OpenCV`-wrapper-library to simplify much of the bloat associated with current library architecture


## Usage
* This dependency is hosted on [packagecloud.io](https://packagecloud.io/git-leon/utils/packages/)

### Step 1 - Add Maven Repository to `pom.xml`
* Because this dependency is hosted on a private server, not MavenCentral, the `pom.xml` must be configured to search in the proper repository.

```xml
<repositories>
  <repository>
    <id>git-leon-utils</id>
    <url>https://packagecloud.io/git-leon/utils/maven2</url>
    <releases>
      <enabled>true</enabled>
    </releases>
    <snapshots>
      <enabled>true</enabled>
    </snapshots>
  </repository>
</repositories>
```

### Step 2 - Add Maven Dependency to `pom.xml`
* To use this project, add the dependency to your `pom.xml`

```xml
<dependency>
  <groupId>com.github.git-leon</groupId>
  <artifactId>opencvl</artifactId>
  <version>1.0</version>
</dependency>
```


<hr><hr>

## Software Dependencies
* To run this project you must install [opencv]().


### OSX Installation
* If you are running OSX, it is recommended that you
	1. Install XCode
	2. Install HomeBrew
	3. Install OpenCV


#### Installing XCode

#### Installing HomeBrew


#### Installing OpenCV
* `brew` an installation of `opencv` version 3.
    * `brew install opencv@3`
    * Should you have difficulties finding the correct HomeBrew formula, I have provided you with an exact copy [here](https://gist.github.com/Git-Leon/d69f62f72b825d7594ef7fa92c408498)

* Edit _OpenCV formula_ to enable java support.
    * `brew edit opencv`
        * change property from `-DBUILD_opencv_java=OFF` to `-DBUILD_opencv_java=ON`
* `brew` an installation of `opencv` from local source
    * `brew install --build-from-source opencv`

#### Checking OpenCV java installation
* Ensure a version of OpenCV can be found in your local machine directory. (Edit the directory version)
    * `/usr/local/Cellar/opencv/3.x.x/share/OpenCV/java/`
    






<hr>

###  Ubuntu Mint Installation (in development)
* `sudo apt-get install`






<hr>

### Raspbeian Pi Installation (in development)
* `sudo apt-get install`






<hr>

### Windows 7 - 11 Installation (in development)
* Navigate to
