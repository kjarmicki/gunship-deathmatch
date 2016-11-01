package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.basis.GenericVisibleThing;
import com.github.kjarmicki.ship.Ship;

import java.util.HashMap;
import java.util.Map;

public abstract class GenericPart extends GenericVisibleThing implements Part {
    protected float condition = 100f;
    protected final Map<PartSlotName, Part> subparts = new HashMap<>();

    public GenericPart(Polygon takenArea, TextureRegion skinRegion) {
        super(takenArea, skinRegion);
    }

    @Override
    public void receiveDamage(float amount) {
        condition -= amount;
    }

    @Override
    public boolean isDestroyed() {
        return condition <= 0;
    }

    @Override
    public void mountSubpart(Part subpart) {
        subparts.put(subpart.getSlotName(), subpart);
        subpart.rotate(this.getRotation());
    }

    public Map<PartSlotName, Part> getAllSubparts() {
        Map<PartSlotName, Part> combined = new HashMap<>();
        combined.putAll(subparts);
        subparts.entrySet().stream().forEach(entry -> {
            Part subpart = entry.getValue();
            combined.putAll(subpart.getAllSubparts());
        });
        return combined;
    }

    public Map<PartSlotName, Part> getDirectSubparts() {
        return subparts;
    }
}
