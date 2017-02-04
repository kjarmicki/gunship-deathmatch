package com.github.kjarmicki.ship;

import com.badlogic.gdx.math.Vector2;
import com.github.kjarmicki.assets.PartSkin;
import com.github.kjarmicki.ship.parts.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class ShipStructure {
    private CorePart core;
    private PartSkin partSkin;

    public ShipStructure(CorePart core) {
        this.core = core;
    }

    public static ShipStructure defaultStructure(Vector2 position) {
        ShipStructure structure = new ShipStructure(new BasicCorePart(position.x, position.y));
        structure.mountPart(new BasicNosePart());
        structure.mountPart(ArmoredWingPart.getLeftVariant());
        structure.mountPart(ArmoredWingPart.getRightVariant());
        structure.mountPart(BasicEnginePart.getLeftVariant());
        structure.mountPart(BasicEnginePart.getRightVariant());
        structure.mountPart(BasicPrimaryWeaponPart.getLeftVariant());
        structure.mountPart(BasicPrimaryWeaponPart.getRightVariant());
        structure.mountPart(BasicSecondaryWeaponPart.getLeftVariant());
        structure.mountPart(BasicSecondaryWeaponPart.getRightVariant());
        return structure;
    }

    public CorePart getCore() {
        return core;
    }

    public ShipStructure mountPart(Part part) {
        findPartWithSlotAvailableFor(part).ifPresent(parentPart -> parentPart.mountSubpart(part));
        part.positionWithinStructure(this);
        if(partSkin != null) part.setPartSkin(partSkin);
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

    public ShipStructure duplicate() {
        CorePart duplicatedCore = (CorePart)core.duplicate();
        List<Part> duplicatedParts = allParts().stream()
                .filter(part -> !part.equals(core))
                .map(Part::duplicate)
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

    public void setPartSkin(PartSkin partSkin) {
        this.partSkin = partSkin;
        allParts().stream()
                .forEach(part -> part.setPartSkin(partSkin));
    }
}
