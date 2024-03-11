package com.zkteco.lakesidehotel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zkteco.lakesidehotel.exception.InvalidBookingRequestException;
import com.zkteco.lakesidehotel.model.BookedRoom;
import com.zkteco.lakesidehotel.model.Room;
import com.zkteco.lakesidehotel.repository.BookingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {

    private final BookingRepository bookingRepository;

    private final IRoomService roomService;

    @Override
    public List<BookedRoom> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    @Override
    public String saveBooking(Long roomId, BookedRoom bookingRequest) {
        if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
            throw new InvalidBookingRequestException("Check-in date must come before check-out date");
        }
        Room room = roomService.getRoomById(roomId).get();
        List<BookedRoom> existingBookings = room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);
        if (roomIsAvailable) {
            room.addBooking(bookingRequest);
            bookingRepository.save(bookingRequest);
        } else {
            throw new InvalidBookingRequestException("Sorry, This roomis not available for the selected dates");
        }
        return bookingRequest.getBookingConfirmationCode();

    }

    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
        return bookingRepository.findByBookingConfirmationCode(confirmationCode);
    }

    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking -> bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                        || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                        || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                        || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                        || (bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))
                        || (bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))
                        || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate())));

    }

}
