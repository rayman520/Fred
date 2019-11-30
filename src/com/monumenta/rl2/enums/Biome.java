package com.monumenta.rl2.enums;

import java.util.Random;

public enum Biome {
    NONE,
    ANY,
    WATER,
    LUSH,
    CITY,
    VAULT;

    public static Biome getRandom() {
        Random random = new Random();
        return values()[random.nextInt(3) + 2];
    }
}

