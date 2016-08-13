package com.github.kjarmicki.model

import spock.lang.Specification

class ShipModelSpec extends Specification {
    def 'should be able to move forwards'() {
        given:
        ShipModel ship = ship()

        when:
        ship.moveForwards()

        then:
        ship.y == 10
        ship.x == 0
        ship.rotation == 0
    }

    def 'should be able to move backwards'() {
        given:
        ShipModel ship = ship()

        when:
        ship.moveBackwards()

        then:
        ship.y == -10
        ship.x == 0
        ship.rotation == 0
    }

    def 'should be able to rotate right'() {
        given:
        ShipModel ship = ship()

        when:
        ship.rotateRight()

        then:
        ship.y == 0
        ship.x == 0
        ship.rotation == -10
    }

    def 'should be able to rotate left'() {
        given:
        ShipModel ship = ship()

        when:
        ship.rotateLeft()

        then:
        ship.y == 0
        ship.x == 0
        ship.rotation == 10
    }

    static ShipModel ship() {
        new ShipModel(0, 0)
    }
}
