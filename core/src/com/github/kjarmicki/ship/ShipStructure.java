package com.github.kjarmicki.ship;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.ship.parts.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class ShipStructure {
    private final CorePart core;

    public ShipStructure(CorePart core) {
        this.core = core;
    }

    public CorePart getCore() {
        return core;
    }

    public void mountPart(Part part) {
        findPartWithSlotAvailableFor(part).ifPresent(parentPart -> parentPart.mountSubpart(part));
    }

    public List<Part> allParts() {
        List<Part> parts = new ArrayList<>(core.getAllSubparts().values());
        parts.add(core);
        parts.sort((p1, p2) -> p1.getZIndex() - p2.getZIndex());
        return parts;
    }

    public List<WeaponPart> weapons() {
        return allParts().stream()
                .filter(part -> part instanceof WeaponPart)
                .map(part -> (WeaponPart)part)
                .collect(toList());
    }

    public void removePart(Part lookedFor) {
        removePart(lookedFor, core);
    }

    public Optional<Part> getPartBySlotName(PartSlotName name) {
        if(name == PartSlotName.CORE) return Optional.of(core);
        return allParts()
                .stream()
                .filter(part -> part.getSlotName().equals(name))
                .findFirst();
    }

    private void removePart(Part lookedFor, Part parent) {
        Map<PartSlotName, Part> subparts = parent.getDirectSubparts();

        // search part at current level
        for(Map.Entry<PartSlotName, Part> entry: subparts.entrySet()) {
            if(entry.getValue().equals(lookedFor)) {
                subparts.remove(entry.getKey());
                return;
            }
        }

        // dig into subparts to find a part
        for(Map.Entry<PartSlotName, Part> entry: subparts.entrySet()) {
            removePart(lookedFor, entry.getValue());
        }
    }

    private Optional<Part> findPartWithSlotAvailableFor(Part otherPart) {
        return allParts().stream()
                .filter(part -> part.getChildSlotNames().contains(otherPart.getSlotName()))
                .findFirst();
    }

}
