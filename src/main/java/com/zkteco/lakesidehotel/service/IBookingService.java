package com.zkteco.lakesidehotel.service;

import java.util.List;

import com.zkteco.lakesidehotel.model.BookedRoom;

public interface IBookingService {

    void cancelBooking(Long bookingId);

    String saveBooking(Long roomId, BookedRoom bookingReequest);

    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    List<BookedRoom> getAllBookings();

    public List<BookedRoom> getAllBookingsByRoomId(Long roomId);

}
