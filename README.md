<h1 align="center">Unikko</h1>
<p align="center">
<img src="https://raw.githubusercontent.com/jnkyto/Unikko/main/src/main/resources/assets/unikko/unikko.png" width="20%" alt="The logo of Unikko Utility Mod">
</p>

<a href="https://github.com/jnkyto/Unikko/actions/workflows/build.yml">
    <p align="center">
        <img src="https://github.com/jnkyto/Unikko/actions/workflows/build.yml/badge.svg" alt="Build status">
    </p>
</a>

---

<b>Unikko</b> is a utility mod for Minecraft 1.16.5. It's features include Discord Rich Presence and some commands. This mod is mainly for my personal learning purposes; don't expect 5-star quality and daily updates.

Unikko is also available on 1.17.1. Just switch the branch from 'main' to '1.17' to view.

### Building from source
Clone the repository or download it as a zip file. Run 'gradlew build' with command line in project root folder. Four .jar files should show up in build/libs. The working mod is the only one without a suffix.

### Installing
1. Install Fabric Loader from https://fabricmc.net/use
2. Download Fabric API from https://www.curseforge.com/minecraft/mc-mods/fabric-api and drag it into %appdata%/.minecraft/mods
3. Drag unikko-1.16.5-1.x.x.jar into %appdata%/.minecraft/mods


### Planned features
- Redo module toggling functionality
- Add GUI and means to edit it
- More "useful" commands

### Thanks to
- Earthcomputer for https://github.com/Earthcomputer/clientcommands
- CottonMC for https://github.com/CottonMC/LibGui
- amitojsingh366 for https://github.com/amitojsingh366/Amitojs-Minecraft-RPC-Fabric
- MinnDevelopment for https://github.com/MinnDevelopment/java-discord-rpc
