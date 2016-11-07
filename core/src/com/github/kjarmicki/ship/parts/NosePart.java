package com.github.kjarmicki.ship.parts;

import static com.github.kjarmicki.ship.parts.PartSlotName.NOSE;

public interface NosePart extends Part {
    default PartSlotName getSlotName() {
        return NOSE;
    }
}
