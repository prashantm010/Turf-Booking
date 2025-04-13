package com.acko.models;

import com.acko.enums.BookingStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Booking {
    String id;
    Slot slot;
    User user;
    Turf turf;
    BookingStatus status;
}
