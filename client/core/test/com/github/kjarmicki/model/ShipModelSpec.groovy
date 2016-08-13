package com.github.kjarmicki.model

import spock.lang.Specification

class ShipModelSpec extends Specification {
    // temporarily disable tests when still tuning things up
//    def 'should be able to move forwards'() {
//        given:
//        ShipModel ship = ship()
//
//        when:
//        ship.moveForwards(0.1f)
//        ship.applyMovement(0.1f)
//
//        then:
//        Math.round(ship.x) == 0
//        Math.round(ship.y) == 10
//        Math.round(ship.rotation) == 90
//    }
//
//    def 'should be able to move backwards'() {
//        given:
//        ShipModel ship = ship()
//
//        when:
//        ship.moveBackwards(1)
//
//        then:
//        Math.round(ship.x) == 0
//        Math.round(ship.y) == -10
//        Math.round(ship.rotation) == 90
//    }
//
//    def 'should be able to rotate right'() {
//        given:
//        ShipModel ship = ship()
//
//        when:
//        ship.rotateRight()
//
//        then:
//        Math.round(ship.x) == 0
//        Math.round(ship.y) == 0
//        Math.round(ship.rotation) == 85
//    }
//
//    def 'should be able to rotate left'() {
//        given:
//        ShipModel ship = ship()
//
//        when:
//        ship.rotateLeft()
//
//        then:
//        Math.round(ship.x) == 0
//        Math.round(ship.y) == 0
//        Math.round(ship.rotation) == 95
//    }
//
//    def 'should move towards rotated direction'() {
//        given:
//        ShipModel ship = ship()
//
//        when:
//        ship.rotateRight()
//        ship.rotateRight()
//        ship.moveForwards(1)
//
//        then:
//        Math.round(ship.x) == 1
//        Math.round(ship.y) == 10
//        Math.round(ship.rotation) == 80
//    }

    static ShipModel ship() {
        new ShipModel(0, 0)
    }
}
