package com.github.kjarmicki.ship

import com.github.kjarmicki.ship.parts.BasicCorePart
import com.github.kjarmicki.ship.parts.BasicEnginePart
import com.github.kjarmicki.ship.parts.BasicWingPart
import spock.lang.Specification

class ShipStructureSpec extends Specification {
    def 'ShipStructure should duplicate same amount of subparts'() {
        given:
        def core = new BasicCorePart(0, 0, null)
        def structure = new ShipStructure(core)
        def leftWing = BasicWingPart.getLeftVariant(null)
        def leftEngine = BasicEnginePart.getLeftVariant(null)
        def rightWing = BasicWingPart.getRightVariant(null)
        def rightEngine = BasicEnginePart.getRightVariant(null)
        structure.mountPart(leftWing)
        structure.mountPart(leftEngine)
        structure.mountPart(rightWing)
        structure.mountPart(rightEngine)

        when:
        def duplicate = structure.duplicate()

        then:
        structure.allParts().size() == duplicate.allParts().size()
    }
}
