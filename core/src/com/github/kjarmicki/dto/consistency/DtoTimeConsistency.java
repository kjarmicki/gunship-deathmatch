package com.github.kjarmicki.dto.consistency;

import com.github.kjarmicki.dto.TimestampedDto;

import java.time.Instant;

public class DtoTimeConsistency {
    private Instant lastRecordedTimestamp = Instant.EPOCH;

    public boolean wasSentAfterLastOne(TimestampedDto dto) {
        Instant currentDtoTimestamp = Instant.ofEpochMilli(dto.getTimestamp());
        return currentDtoTimestamp.isAfter(lastRecordedTimestamp);
    }

    public void recordLastTimestamp(TimestampedDto dto) {
        lastRecordedTimestamp = Instant.ofEpochMilli(dto.getTimestamp());
    }

    public static long timestamp() {
        return Instant.now().toEpochMilli();
    }
}
