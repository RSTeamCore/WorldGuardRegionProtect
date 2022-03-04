rootProject.name = "WorldGuardRegionProtect"

include("wgrp-api")
include("wgrp-bukkit")

listOf("api", "bukkit").forEach {
    include("wgrp-api:$it")
}
include("wgrp-api:core:ap")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
