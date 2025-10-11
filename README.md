# SnowTweaks For 1.2.5

**Overview**:

SnowTweaks is a Minecraft mod for 'golden age' versions that changes how snow works and reacts. By default snow and ice no longer melt (configurable). To prevent snowfall in an area you right click snow with any of the new 'Snow Shovel' tools. This will spawn a 'ghost snow' block which is invisible and acts as if nothing is there, but will stop new snow from falling. Left clicking with the snow shovel harvests snow off the ground, a previously unobtainable block. Snow shovels also can harvest snow blocks, and the diamond one can even harvest ice.

**Features**:

* Configure if snow and ice melt (independent of each other). If you choose for them to not melt, new snow and ice will fall and form in areas with a high light level
* Obtainable snow and ice
* New snow shovel tool
* Full control over where snow is and can fall
* *Fully* compatible with Tekkit Classic 3.1.2 out of the box! Equivalent Exchange 2 integration, Industrial Craft 2 integration (bronze snow shovel), and ProjectRed integration (ruby, emerald, and sapphire snow shovels)!

**IMPORTANT**: If you are installing this mod on a Tekkit Classic *server* you need the [Bukkit](https://github.com/maxresd3fault/SnowTweaks/tree/1.2.5-(Bukkit)) version here.

**Requirements**:

* [Forge (3.3.8.152)](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.2.5.html)

**Installation**:

This mod needs to be installed as a jarmod as it modifies 3 base classes. The following classes are edited:

| Unobfuscated Name      | Client     | Server     |
|------------------------|------------|------------|
| `BlockIce.class`       | `jz.class` | `ov.class` |
| `BlockSnow.class`      | `ha.class` | `lg.class` |
| `BlockSnowBlock.class` | `aa.class` | `ah.class` |
