package com.github.kjarmicki.notices;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class NoticesOutput {
    public static final int MAX_KEPT = 5;
    public static final Duration KEEP_FOR = Duration.ofSeconds(5);
    private List<Map.Entry<Instant, String>> noticesInTime;

    public NoticesOutput(List<Map.Entry<Instant, String>> noticesInTime) {
        this.noticesInTime = noticesInTime;
    }

    public NoticesOutput() {
        this(new ArrayList<>());
    }

    public void add(String notice) {
        Instant now = Instant.now();
        Map.Entry<Instant, String> noticeInTime = new Map.Entry<Instant, String>() {
            public Instant getKey() {
                return now;
            }
            public String getValue() {
                return notice;
            }
            public String setValue(String value) {
                throw new UnsupportedOperationException("Cannot set value for this entry");
            }
        };
        noticesInTime.add(noticeInTime);
    }

    public void update() {
        capeOverMax();
        capeOutdated();
    }

    public List<String> getAll() {
        return noticesInTime.stream()
                .map(Map.Entry::getValue)
                .collect(toList());
    }

    private void capeOverMax() {
        if(noticesInTime.size() > MAX_KEPT) {
            noticesInTime =
                    noticesInTime.subList(noticesInTime.size() - MAX_KEPT, noticesInTime.size());
        }
    }

    private void capeOutdated() {
        Instant now = Instant.now();
        noticesInTime = noticesInTime.stream()
                .filter(notice -> now.isBefore(notice.getKey().plus(KEEP_FOR)))
                .collect(toList());
    }
}
