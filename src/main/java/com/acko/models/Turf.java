package com.acko.models;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Turf {
    private String name;
    private User user;
    private List<Slot> slots;

    public void generateSlots(Date date) {
        // Logic to generate slots for the turf
        // For example, creating time slots for a day
        slots = new ArrayList<>();
        for (int hour = 10; hour < 22; hour += 2) {
            String time = hour + ":00 - " + (hour + 2) + ":00";
            Slot slot = Slot.builder()
                    .id(UUID.randomUUID().toString())
                    .date(date)
                    .time(time)
                    .isAvailable(true)
                    .build();
            slots.add(slot);
        }
    }
}
