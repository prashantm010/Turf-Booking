package com.acko;

import com.acko.models.Booking;
import com.acko.models.Slot;
import com.acko.models.Turf;
import com.acko.models.User;
import com.acko.services.BookingService;
import com.acko.services.TurfService;
import com.acko.services.UserService;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        UserService userService = new UserService();
        TurfService turfService = new TurfService();
        BookingService bookingService = new BookingService(turfService);

        // Create User
        User user = User.builder().name("prashant").isOwner(true).build();
        userService.createUser(user);

        // Add a turf
        Turf turf = Turf.builder().name("Turf HSR").user(user).build();
        turfService.addTurf(turf);

        // Generate slots for the turf
        Date date = new GregorianCalendar(2025, Calendar.APRIL, 6).getTime();
        turfService.generateSlots(turf, date);

        // Create User
        User user2 = User.builder().name("Raghav").isOwner(false).build();
        userService.createUser(user2);

        // List All Available Slots
        List<Slot> slots = turfService.getAvailableSlots(turf, date);
        System.out.println("Printing Available Slots");
        slots.forEach(System.out::println);

        // Booking for cancellation
        Booking booking1 = null;
        if (!slots.isEmpty()) {
            // Book a slot
            Slot slot = slots.get(0);
            booking1 = Booking.builder()
                    .user(user2)
                    .turf(turf)
                    .slot(slot)
                    .build();
            bookingService.bookSlot(booking1);
            System.out.println("Booking Done Successfully");
        }

        List<Slot> slots2 = turfService.getAvailableSlots(turf, date);
        slots2.forEach(System.out::println);
        if (Objects.nonNull(slots2)) {
            // Book a slot
            Random random = new Random();
            Slot slot2 = slots.get(random.nextInt(slots.size()));
            Booking booking2 = Booking.builder()
                    .user(user2)
                    .turf(turf)
                    .slot(slot2)
                    .build();
            bookingService.bookSlot(booking2);
            System.out.println("Booking Done Successfully");
        }

        turfService.getAvailableSlots(turf, date).forEach(System.out::println);

        if (!slots.isEmpty() && Objects.nonNull(booking1)) {
            bookingService.cancelBooking(booking1);
            System.out.println("Booking Cancelled Successfully");
        }

        turfService.getAvailableSlots(turf, date).forEach(System.out::println);

        // View User bookings
        List<Booking> bookings = bookingService.viewBookings(user2);
        System.out.println("Printing Bookings By End User");
        if (Objects.nonNull(bookings)) {
            bookings.forEach(System.out::println);
        }

        // View All Bookings as Turf Owner
        List<Booking> turfOwnerBookings = bookingService.listAllTurfBookingForOwner(user, turf);
        System.out.println("Printing Bookings By Owner");
        if (Objects.nonNull(turfOwnerBookings)) {
            turfOwnerBookings.forEach(System.out::println);
        }
    }
}