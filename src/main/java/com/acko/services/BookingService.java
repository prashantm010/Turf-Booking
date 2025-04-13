package com.acko.services;

import com.acko.enums.BookingStatus;
import com.acko.models.Booking;
import com.acko.models.Slot;
import com.acko.models.Turf;
import com.acko.models.User;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BookingService {
    Map<User, List<Booking>> userBookings = new HashMap<>();
    private final TurfService turfService;
    
    // Method to book a slot
    public void bookSlot(Booking booking) {
        // Logic to book a slot
        // Check if the slot is available
        turfService.isTurfValid(booking.getTurf());
        if (!turfService.isSlotAvailable(booking.getTurf(), booking.getSlot())) {
            throw new IllegalArgumentException("Slot not available");
        }
        booking.getSlot().setAvailable(false);
        booking.setId(UUID.randomUUID().toString());
        booking.setStatus(BookingStatus.BOOKED);
        if (userBookings.containsKey(booking.getUser())) {
            userBookings.get(booking.getUser()).add(booking);
        } else {
            userBookings.put(booking.getUser(), new ArrayList<>(List.of(booking)));
        }
    }

    // Method to cancel a booking
    public void cancelBooking(Booking booking) {
        // Logic to cancel a booking
        for (Map.Entry<User, List<Booking>> entry : userBookings.entrySet()) {
            List<Booking> bookings = entry.getValue();
            for (Booking booking1 : bookings) {
                if (booking1.getId().equals(booking.getId())) {
                    booking.getSlot().setAvailable(true);
                    booking.setStatus(BookingStatus.CANCELLED);
                    return;
                }
            }
        }
    }

    // Method to view bookings
    public List<Booking> viewBookings(User user) {
        // Logic to view bookings
        return userBookings.get(user);
    }

    public List<Booking> listAllTurfBookingForOwner(User user, Turf turf) {
        // Logic to list all turf bookings for the owner
        if (Boolean.FALSE.equals(user.getIsOwner())) {
            throw new IllegalArgumentException("User is not an owner");
        }

        List<Turf> turfs = turfService.turfManager.get(user);
        if (turfs == null || !turfs.contains(turf)) {
            throw new IllegalArgumentException("User does not own this turf");
        }
        return userBookings.values().stream()
                .flatMap(Collection::stream)
                .filter(booking -> booking.getTurf().equals(turf))
                .collect(Collectors.toList());
    }
}
