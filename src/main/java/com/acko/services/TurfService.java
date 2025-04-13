package com.acko.services;

import com.acko.exceptions.InvalidTurfException;
import com.acko.exceptions.InvalidUserException;
import com.acko.exceptions.SlotsNotAvailableException;
import com.acko.models.Booking;
import com.acko.models.Slot;
import com.acko.models.Turf;
import com.acko.models.User;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class TurfService {
    Map<User, List<Turf>> turfManager = new HashMap<>();

    // Example method to add a turf
    public void addTurf(Turf turf) {
        // Logic to add a turf
        if (Boolean.FALSE.equals(turf.getUser().getIsOwner())) {
            throw new InvalidUserException("User is not an owner");
        }
        if (turfManager.containsKey(turf.getUser())) {
            turfManager.get(turf.getUser()).add(turf);
        } else {
            turfManager.put(turf.getUser(), List.of(turf));
        }
    }

    public void generateSlots(Turf turf, Date date) {
        turf.generateSlots(date);
    }

    // Example method to get available slots for a turf
    public List<Slot> getAvailableSlots(Turf turf, Date date) {
        List<Slot> slots = turf.getSlots().stream()
                .filter(slot -> slot.getDate().equals(date))
                .filter(Slot::isAvailable)
                .toList();
        if (slots.isEmpty()) {
            throw new SlotsNotAvailableException("No slots available for the selected date");
        }
        return slots;
    }

    public void isTurfValid(Turf turf) {
        if (!turfManager.containsKey(turf.getUser())) {
            throw new InvalidTurfException("Invalid turf ID");
        }
        for (Map.Entry<User, List<Turf>> entry : turfManager.entrySet()) {
            if (entry.getValue().contains(turf)) {
                return;
            }
        }
        throw new InvalidTurfException("Invalid turf ID");
    }
    
    public Boolean isSlotAvailable(Turf turf, Slot slot) {
        for (Turf t : turfManager.get(turf.getUser())) {
            if (t.getSlots().contains(slot)) {
                return slot.isAvailable();
            }
        }
        return false;
    }
}
