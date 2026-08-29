package com.airtribe.librarymanagementsystem.observer;

import com.airtribe.librarymanagementsystem.entity.Reservation;

public class PatronNotifier implements NotificationObserver{
    @Override
    public void onBookAvailability(Reservation reservation) {
        System.out.println("Book is now available "+ reservation.getISBN() +", "+ reservation.getPatronId() + " please checkout it");
    }
}
