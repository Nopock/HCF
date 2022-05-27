package me.nopox.hcf.bitmask;

import lombok.Getter;

@Getter
public enum Bitmask {

    // Used in spawns
    SAFE_ZONE(1, "Safe-Zone", "Determines if a region is considered completely safe"),

    // Used in Citadel
    CITADEL(32, "Citadel", "Determines if a region is part of Citadel"),

    // Used in KOTHs
    KOTH(64, "KOTH", "Determines if a region is a KOTH"),

    // Used in KOTHs & Citadel.
    REDUCED_DTR_LOSS(128, "Reduced-DTR-Loss", "Determines if a region takes away reduced DTR upon death"),

    // Used in various regions.
    NO_ENDERPEARL(256, "No-Enderpearl", "Determines if a region cannot be pearled into"),

    // Used on the road.
    ROAD(1024, "Road", "Determines if a region is a road."),

    // Used in Conquest.
    CONQUEST(2048, "Conquest", "Determines if a region is part of Conquest.");

    private int dtr;
    private String name, description;

    Bitmask(int dtr, String name, String description) {
        this.dtr = dtr;
        this.name = name;
        this.description = description;
    }


}
