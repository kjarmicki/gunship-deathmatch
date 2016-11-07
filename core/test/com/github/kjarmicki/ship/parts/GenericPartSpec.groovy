package com.github.kjarmicki.ship.parts

import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.github.kjarmicki.ship.ShipFeatures
import spock.lang.Specification

class GenericPartSpec extends Specification {

    def 'should correctly compute outside bounds'() {
        expect:
        part.outsideBounds(bounds) == vector

        where:
        part        | bounds                            | vector
        part()      | new Rectangle(2, 0, 10, 10)       | new Vector2(2, 0)
        part()      | new Rectangle(0, 2, 10, 10)       | new Vector2(0, 2)
        part()      | new Rectangle(2, 2, 10, 10)       | new Vector2(2, 2)

        part()      | new Rectangle(0, 0, 8, 10)        | new Vector2(-2, 0)
        part()      | new Rectangle(0, 0, 10, 8)        | new Vector2(0, -2)
        part()      | new Rectangle(0, 0, 8, 8)         | new Vector2(-2, -2)

        part()      | new Rectangle(0, 0, 10, 10)        | new Vector2(0, 0)

    }

    static GenericPart part() {
        new GenericPart(new Polygon(0, 0, 0, 10, 10, 0, 10, 10), null) {
            @Override
            float getWidth() {
                return null
            }

            @Override
            float getHeight() {
                return null
            }

            @Override
            boolean isCritical() {
                return false
            }

            @Override
            int getZIndex() {
                return 0
            }

            @Override
            void updateFeatures(ShipFeatures features) {

            }
        }
    }
}
