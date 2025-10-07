# SnowTweaks

**Overview**:

SnowTweaks is a Minecraft mod for 'golden age' versions that changes how snow works and reacts. By default snow and ice no longer melt (configurable). To prevent snowfall in an area you right click snow with any of the new 'Snow Shovel' tools. This will spawn a 'ghost snow' block which is invisible and acts as if nothing is there, but will stop new snow from falling. Left clicking with the snow shovel harvests snow off the ground, a previously unobtainable block. Snow shovels also can harvest snow blocks, and the diamond one can even harvest ice.

**Features**:

* Configure if snow and ice melt (independent of each other). If you choose for them to not melt, new snow and ice will fall and form in areas with a high light level
* Obtainable snow and ice
* New snow shovel tool
* Full control over where snow is and can fall
* Compatible with ChunkLoader+
* If IndustrialCraft is installed, a bronze snow shovel is added (optional dependency)

**Requirements**:

* [ModLoaderMP](https://mcarchive.net/mods/modloadermp)
* [Forge](https://mcarchive.net/mods/minecraftforge?gvsn=)
* [MaxresBase](https://github.com/maxresd3fault/MaxresBase)

**Installation**:

The mod can either be installed as a standalone mod or as a jar mod. It also requires my helper mod, MaxresBase, which provides important overrides via reflector allow this mod to function. You must install the following base classes:

| Client     | Server     |
|------------|------------|
| `ac.class` | `gj.class` |
| `jr.class` | `ik.class` |
| `nk.class` | `p.class`  |
