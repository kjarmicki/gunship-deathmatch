package com.github.kjarmicki.notices;

import com.github.kjarmicki.player.Player;

import java.util.ArrayList;
import java.util.List;

public class NoticesInput {
    private final List<String> notices;

    public NoticesInput(List<String> notices) {
        this.notices = notices;
    }

    public NoticesInput() {
        this(new ArrayList<>());
    }

    public void playerDestroyedOtherPlayer(Player destroyed, Player destructor) {
        notices.add(destructor.getName() + " blown " + destroyed.getName() + " into bits");
    }

    public void playerJoined(Player joiner) {
        notices.add(joiner.getName() + " joined the battle");
    }

    public void playerLeft(Player player) {
        notices.add(player.getName() + " left the battle");
    }

    public List<String> getAllAndClear() {
        List<String> copy = new ArrayList<>(notices);
        notices.clear();
        return copy;
    }
}
