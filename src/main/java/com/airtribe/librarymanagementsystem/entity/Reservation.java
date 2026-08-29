package com.airtribe.librarymanagementsystem.entity;

import java.time.LocalDate;

public class Reservation {
    private final String ISBN;
    private final String patronId;
    private final LocalDate reservationDate;
    private ReservationStatus reservationStatus;

    public Reservation(String ISBN, String patronId, LocalDate reservationDate, ReservationStatus reservationStatus) {
        this.ISBN = ISBN;
        this.patronId = patronId;
        this.reservationDate = reservationDate;
        this.reservationStatus = reservationStatus;
    }

    public void markAsFulfilled() {
        this.reservationStatus = ReservationStatus.FULFILLED;
    }
    public void markAsCancelled() {
        this.reservationStatus = ReservationStatus.CANCELLED;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getPatronId() {
        return patronId;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "ISBN='" + ISBN + '\'' +
                ", patronId='" + patronId + '\'' +
                ", reservationDate=" + reservationDate +
                ", reservationStatus=" + reservationStatus +
                '}';
    }
}
