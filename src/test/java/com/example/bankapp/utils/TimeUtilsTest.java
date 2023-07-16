package com.example.bankapp.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeUtilsTest {

    private TimeUtils timeUtils;

    @BeforeEach
    void setUp() {
        timeUtils = new TimeUtils();
    }

    @Test
    public void get_currently_time_correctly() {
        assertEquals(OffsetDateTime.now().getDayOfMonth(), timeUtils.getCurrentlyTime().getDayOfMonth());
        assertEquals(OffsetDateTime.now().getMonthValue(), timeUtils.getCurrentlyTime().getMonthValue());
        assertEquals(OffsetDateTime.now().getYear(), timeUtils.getCurrentlyTime().getYear());
    }

}