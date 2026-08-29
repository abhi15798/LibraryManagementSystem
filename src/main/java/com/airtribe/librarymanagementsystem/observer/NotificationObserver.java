package com.airtribe.librarymanagementsystem.observer;

import com.airtribe.librarymanagementsystem.entity.Reservation;

public interface NotificationObserver {
    void onBookAvailability(Reservation reservation);
}
