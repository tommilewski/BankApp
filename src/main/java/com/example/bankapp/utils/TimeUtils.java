package com.example.bankapp.utils;

import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class TimeUtils {

    public OffsetDateTime getCurrentlyTime() {
        return OffsetDateTime.now();
    }
}
