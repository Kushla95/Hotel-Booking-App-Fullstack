package com.zkteco.lakesidehotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zkteco.lakesidehotel.model.BookedRoom;

public interface BookingRepository extends JpaRepository<BookedRoom, Long> {

    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    List<BookedRoom> findByRoomId(Long roomId);

}
