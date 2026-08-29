package com.airtribe.librarymanagementsystem.service;

import com.airtribe.librarymanagementsystem.entity.Reservation;
import com.airtribe.librarymanagementsystem.entity.ReservationStatus;
import com.airtribe.librarymanagementsystem.inventory.ReservationInventory;
import com.airtribe.librarymanagementsystem.observer.NotificationObserver;
import java.time.LocalDate;
import java.util.*;

public class ReservationService {
    private List<NotificationObserver> observerList;
    private final ReservationInventory reservationInventory;
    public ReservationService(ReservationInventory reservationInventory){
        this.observerList = new ArrayList<>();
        this.reservationInventory=reservationInventory;
    }
    public void addObserver(NotificationObserver notificationObserver){
        observerList.add(notificationObserver);
    }
    public void removeObserver(NotificationObserver notificationObserver){
        observerList.remove(notificationObserver);
    }
    public void reserve(String ISBN,String patronId){
        Reservation res = new Reservation(ISBN,patronId, LocalDate.now(), ReservationStatus.WAITING);
        reservationInventory.addReservation(res);
    }
    public void notifyReserve(String ISBN){
        if(!reservationInventory.hasWaitingReservation(ISBN)){
            return;
        }
        Reservation reservation =reservationInventory.pollNextReservation(ISBN);
        reservation.markAsFulfilled();
        for(NotificationObserver observer:observerList){
            observer.onBookAvailability(reservation);
        }
    }
}
