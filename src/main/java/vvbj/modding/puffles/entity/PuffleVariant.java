package vvbj.modding.puffles.entity;

import java.util.Arrays;
import java.util.Comparator;

public enum PuffleVariant {
    RED(0),
    BLUE(1),
    GREEN(2),
    YELLOW(3),
    ORANGE(4),
    PURPLE(5),
    BROWN(6),
    PINK(7),
    BLACK(8),
    WHITE(9);

    private static final PuffleVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(PuffleVariant::getId))
            .toArray(PuffleVariant[]::new);
    private final int id;

    PuffleVariant(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public static PuffleVariant byId(int id){
        return BY_ID[id % BY_ID.length];
    }

}
