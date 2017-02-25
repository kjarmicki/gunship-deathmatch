package com.github.kjarmicki.client.hud;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class EventsLog {
    public static final int MAX_KEPT = 5;
    public static final Duration KEEP_FOR = Duration.ofSeconds(10);
    private final List<Map.Entry<Instant, String>> eventsInTime;

    public EventsLog(List<Map.Entry<Instant, String>> eventsInTime) {
        this.eventsInTime = eventsInTime;
    }

    public EventsLog() {
        this(new ArrayList<>());
    }

    public void add(String event) {
        Map.Entry<Instant, String> eventInTime = new Map.Entry<Instant, String>() {
            public Instant getKey() {
                return Instant.now();
            }
            public String getValue() {
                return event;
            }
            public String setValue(String value) {
                throw new UnsupportedOperationException("Cannot set value for this entry");
            }
        };
        eventsInTime.add(eventInTime);
    }

    public void update() {
        Instant now = Instant.now();
        List<Map.Entry<Instant, String>> caped = eventsInTime.stream()
                .filter(event -> event.getKey().plus(KEEP_FOR).isBefore(now))
                .collect(toList());
        eventsInTime.clear();
        eventsInTime.addAll(caped);
    }

    public List<String> getAll() {
        return eventsInTime.stream()
                .map(Map.Entry::getValue)
                .collect(toList());
    }

    private void cape() {
        if(eventsInTime.size() > MAX_KEPT) {
            List<Map.Entry<Instant, String>> caped =
                    eventsInTime.subList(eventsInTime.size() - MAX_KEPT, MAX_KEPT);
            eventsInTime.clear();
            eventsInTime.addAll(caped);
        }
    }
}
