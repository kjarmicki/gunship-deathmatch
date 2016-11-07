package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.basis.GenericVisibleThing;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    public void inheritSubpartsFrom(Part other) {
        Map<PartSlotName, Part> direct = other.getDirectSubparts();
        subparts.clear();
        subparts.putAll(direct);
        direct.entrySet().stream().map(Map.Entry::getValue).forEach(Part::positionWithinOwner);
    }

    @Override
    public void mountSubpart(Part newPart) {
        // grab a hold on the part that's going to be replaced
        Optional<Part> toBeReplaced = Optional.ofNullable(subparts.get(newPart.getSlotName()));
        subparts.put(newPart.getSlotName(), newPart);
        // if there was a part in this slot already, inherit it's subparts
        toBeReplaced.ifPresent(newPart::inheritSubpartsFrom);
        newPart.rotate(this.getRotation());
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
