package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.basis.GenericVisibleThing;

import java.util.HashMap;
import java.util.Map;

public abstract class GenericPart extends GenericVisibleThing implements Part {
    protected float condition = 100f;
    protected final Map<String, Part> subparts = new HashMap<>();

    public GenericPart(Polygon takenArea, TextureRegion skinRegion) {
        super(takenArea, skinRegion);
    }

    public void receiveDamage(float amount) {
        condition -= amount;
    }

    public boolean isDestroyed() {
        return condition <= 0;
    }

    public void mountSubpart(String slot, Part subpart) {
        subparts.put(slot, subpart);
    }

    public Map<String, Part> getAllSubparts() {
        HashMap<String, Part> combined = new HashMap<>();
        combined.putAll(subparts);
        subparts.entrySet().stream().forEach(entry -> {
            Part subpart = entry.getValue();
            combined.putAll(subpart.getAllSubparts());
        });
        return combined;
    }

    public Map<String, Part> getDirectSubparts() {
        return subparts;
    }
}
