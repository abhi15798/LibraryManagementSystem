package com.airtribe.librarymanagementsystem.inventory;

import com.airtribe.librarymanagementsystem.entity.Reservation;

import java.util.*;

public class ReservationInventory {
    private final Map<String, Queue<Reservation>> reservationData;

    public ReservationInventory() {
        this.reservationData = new HashMap<>();
    }
    public void addReservation(Reservation reservation){
        if (reservationData.containsKey(reservation.getISBN())){
            reservationData.get(reservation.getISBN()).add(reservation);
        }
        else{
            Queue<Reservation> waitingList = new LinkedList<>();
            waitingList.add(reservation);
            reservationData.put(reservation.getISBN(), waitingList);
        }
    }
    public Reservation pollNextReservation(String ISBN){
        if(!reservationData.containsKey(ISBN)){
            throw new IllegalStateException("No Reservation exists for this book");
        } else{
            if(reservationData.get(ISBN).isEmpty()) {
                throw new IllegalStateException("No one in the waiting for this book");
            } else{
                  return reservationData.get(ISBN).poll();
              }
          }
    }
    public boolean hasWaitingReservation(String ISBN){
        if(!reservationData.containsKey(ISBN)){
            return false;
        }else{
            return !reservationData.get(ISBN).isEmpty();
        }
    }
}
