package net.eyeballtea.miniman.entities.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum LittleVariant {
    DEFAULT(0),
    PURPLE(1),
    RED(2),
    YELLOW(3);

    private static final LittleVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(LittleVariant::getId)).toArray((variant) -> {
        return new LittleVariant[variant];
    });
    private final int id;

    private LittleVariant(int var) {
        this.id = var;
    }

    public int getId() {
        return this.id;
    }

    public static LittleVariant byId(int vari) {
        return BY_ID[vari % BY_ID.length];
    }
}