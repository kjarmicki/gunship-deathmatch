package com.github.kjarmicki.assets;

public class AssetKey {
    private final String name;
    private final int index;

    public AssetKey(Object name, int index) {
        this.name = name.toString();
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetKey assetKey = (AssetKey) o;

        return index == assetKey.index && name.equals(assetKey.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + index;
        return result;
    }

    @Override
    public String toString() {
        return name + " (" + index + ")";
    }
}
