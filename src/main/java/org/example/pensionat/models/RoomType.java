package org.example.pensionat.models;

import java.util.Arrays;

public enum RoomType {

    SINGLEROOM("Single"),
    DOUBLEROOM("Double");

    private String sort;

    RoomType(String sort) {
        this.sort = sort;
    }

    public String getSort() {
        return sort;
    }

    public static RoomType displayName(String name) {
        return Arrays.stream(values())
                .filter(rt -> rt.sort.equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No RoomType with name: " + name));
    }

}
