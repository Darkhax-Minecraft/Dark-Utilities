<!-- name-start -->
# DarkUtilities [![CurseForge Project](https://img.shields.io/curseforge/dt/242195?logo=curseforge&label=CurseForge&style=flat-square&labelColor=2D2D2D&color=555555)](https://www.curseforge.com/minecraft/mc-mods/dark-utilities) [![Modrinth Project](https://img.shields.io/modrinth/dt/CkqTAIaP?logo=modrinth&label=Modrinth&style=flat-square&labelColor=2D2D2D&color=555555)](https://modrinth.com/mod/dark-utilities) [![Maven Project](https://img.shields.io/maven-metadata/v?style=flat-square&logoColor=D31A38&labelColor=2D2D2D&color=555555&label=Latest&logo=gradle&metadataUrl=https%3A%2F%2Fmaven.blamejared.com%2Fnet%2Fdarkhax%2Fdarkutilities%2Fdarkutils-common-1.21.1%2Fmaven-metadata.xml)](https://maven.blamejared.com/net/darkhax/darkutilities)
<!-- name-end -->
<!-- description-start -->
A bit of everything. The documentation for this mod can be found [here](https://docs.darkhax.net/mods/darkutils).
<!-- description-end -->

<!-- maven-start -->
## Maven Dependency

If you are using [Gradle](https://gradle.org) to manage your dependencies, add the following into your `build.gradle` file. Make sure to replace the version with the correct one. All versions can be viewed [here](https://maven.blamejared.com/net/darkhax/darkutilities).

```gradle
repositories {
    maven { 
        url 'https://maven.blamejared.com'
    }
}

dependencies {
    // NeoForge
    implementation group: 'net.darkhax.darkutilities', name: 'darkutils-neoforge-1.21.1', version: '21.1.0'

    // Forge
    implementation group: 'net.darkhax.darkutilities', name: 'darkutils-forge-1.21.1', version: '21.1.0'

    // Fabric & Quilt
    modImplementation group: 'net.darkhax.darkutilities', name: 'darkutils-fabric-1.21.1', version: '21.1.0'

    // Common / MultiLoader / Vanilla
    compileOnly group: 'net.darkhax.darkutilities', name: 'darkutils-common-1.21.1', version: '21.1.0'
}
```
<!-- maven-end -->

<!-- sponsor-start -->
## Sponsors

[![](https://assets.blamejared.com/nodecraft/darkhax.jpg)](https://nodecraft.com/r/darkhax)    
DarkUtilities is sponsored by Nodecraft. Use code **[DARKHAX](https://nodecraft.com/r/darkhax)** for 30% of your first month of service!
<!-- sponsor-end -->