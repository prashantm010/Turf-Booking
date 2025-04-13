package com.acko.models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Slot {
    String id;
    String time;
    Date date;
    boolean isAvailable;
}
