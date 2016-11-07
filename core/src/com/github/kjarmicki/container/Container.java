package com.github.kjarmicki.container;

import java.util.List;

public interface Container<T> {
    List<T> getContents();
}
