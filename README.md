# ServerRegionProtect

WARN! PLUGIN SUPPORT ONLY JAVA 11+

Opportunities:
- By entering the name of the region in the plugin's config.yml, you will enter it in the plugin's protection. No one can break the region that is registered in config. yml, even the operators are not able to do this!
- plugin can do:
1. Cant place/break blocks.
2. Protect frame, painting from any damage(arrows, trident, snowball, eggs, enderpearl and damaged from player).
3. Protect any change in the frame.
4. Cannot set armour stand and break him.
5. Cannot fill lava/water bucket.
6. TNT, minecart with tnt, Respawn Anchor and ender crystal cant damaged any blocks in protect region.
7. Support for banning dangerous commands. Separate commands were added and tested using FastAsyncWorldEdit in the protected region.
8. Cannot anyone remove protected region just use like "/rg rem your_rg" and etc.
9. Create a special region inside the protected region in which you can break and install or only break (specified in config. yml)

P.S.
In this case, the access rights to the region are registered through this Perm:
- srp. serverregionprotect-access to protected regions
The star gives all the rights, it is already clear, operators are not allowed access. If we want to ban the star, then we simply register this perm in PermissionsEx or LuckPerms(depending on what you have)
