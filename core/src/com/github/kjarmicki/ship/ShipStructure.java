package com.github.kjarmicki.ship;

import com.github.kjarmicki.ship.parts.CorePart;
import com.github.kjarmicki.ship.parts.Part;
import com.github.kjarmicki.ship.parts.PartSlotName;
import com.github.kjarmicki.ship.parts.WeaponPart;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class ShipStructure {
    private final CorePart core;

    public ShipStructure(CorePart core) {
        this.core = core;
    }

    public CorePart getCore() {
        return core;
    }

    public ShipStructure mountPart(Part part) {
        findPartWithSlotAvailableFor(part).ifPresent(parentPart -> parentPart.mountSubpart(part));
        part.positionWithinStructure(this);
        return this;
    }

    public List<Part> allParts() {
        List<Part> parts = core.getAllSubpartsFlat();
        parts.add(core);
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

    public ShipStructure duplicateWithoutOwner() {
        CorePart duplicatedCore = (CorePart)core.duplicateWithoutOwner();
        List<Part> duplicatedParts = allParts().stream()
                .filter(part -> !part.equals(core))
                .map(Part::duplicateWithoutOwner)
                .collect(toList());
        ShipStructure duplicatedStructure = new ShipStructure(duplicatedCore);
        duplicatedParts.stream()
                .forEach(duplicatedStructure::mountPart);

        return duplicatedStructure;
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
