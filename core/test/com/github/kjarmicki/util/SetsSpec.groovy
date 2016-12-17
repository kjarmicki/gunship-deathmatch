package com.github.kjarmicki.util

import spock.lang.Specification

class SetsSpec extends Specification {
    def "Sets helper should be able to combine multiple elements"() {
        when:
        List<List<Integer>> combinations = Sets.combinations([1, 2, 3, 4, 5], 2)

        then:
        combinations == [[1, 2], [1, 3], [1, 4], [1, 5], [2, 3], [2, 4], [2, 5], [3, 4], [3, 5], [4, 5]]
    }

    def "Sets helper should return empty list when amount of elements is lower than set size"() {
        when:
        List<List<Integer>> combinations = Sets.combinations([1, 2], 3)

        then:
        combinations == []
    }
}
