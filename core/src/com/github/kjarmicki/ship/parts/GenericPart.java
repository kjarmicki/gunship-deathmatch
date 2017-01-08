package com.github.kjarmicki.ship.parts;

import com.badlogic.gdx.math.Polygon;
import com.github.kjarmicki.basis.GenericVisibleThing;

import java.util.*;

public abstract class GenericPart extends GenericVisibleThing implements Part {
    protected float condition = 100f;
    protected final Map<PartSlotName, Part> subparts = new HashMap<>();

    public GenericPart(Polygon takenArea) {
        super(takenArea);
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
    public float getCondition() {
        return condition;
    }

    @Override
    public void inheritSubpartsFrom(Part other) {
        Map<PartSlotName, Part> direct = other.getDirectSubparts();
        subparts.clear();
        subparts.putAll(direct);
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

    @Override
    public List<Part> getAllSubpartsFlat() {
        List<Part> combined = new ArrayList<>();
        combined.addAll(subparts.values());
        subparts.entrySet().stream()
                .map(Map.Entry::getValue)
                .forEach(subpart -> {
                    combined.addAll(subpart.getAllSubpartsFlat());
                });
        return combined;
    }

    public Map<PartSlotName, Part> getDirectSubparts() {
        return subparts;
    }
}
