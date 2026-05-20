package com.smarthostel.service;

import com.smarthostel.dao.AllocationDao;
import com.smarthostel.dao.DaoException;
import com.smarthostel.dao.RoomDao;
import com.smarthostel.dao.UserDao;
import com.smarthostel.dao.dto.PendingAllocationInfo;
import com.smarthostel.model.Room;
import com.smarthostel.service.dto.ManageRoomsView;
import com.smarthostel.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RoomAllocationService {

    private final RoomDao roomDao;
    private final AllocationDao allocationDao;
    private final UserDao userDao;

    public RoomAllocationService(RoomDao roomDao, AllocationDao allocationDao, UserDao userDao) {
        this.roomDao = roomDao;
        this.allocationDao = allocationDao;
        this.userDao = userDao;
    }

    public ManageRoomsView loadManageRoomsPage() {
        return new ManageRoomsView(
                roomDao.findAllOrdered(),
                userDao.findStudentsEligibleForAllocation(),
                allocationDao.findPendingRequestsWithDetails());
    }

    public void addRoom(String roomNumber, int capacity) {
        roomDao.insertRoom(roomNumber, capacity);
    }

    public void manualAllocate(int roomId, int userId) {
        try (Connection conn = DBConnection.getConnection()) {
            boolean prior = conn.getAutoCommit();
            conn.setAutoCommit(false);
            try {
                if (allocationDao.hasActiveAllocation(conn, userId)) {
                    throw new DaoException("This student already has an active room allocation.");
                }
                allocationDao.deletePendingForUser(conn, userId);
                if (!roomDao.tryIncrementOccupied(conn, roomId)) {
                    throw new DaoException("Room is full or could not be updated.");
                }
                allocationDao.insertActive(conn, userId, roomId);
                roomDao.syncRoomStatus(conn, roomId);
                conn.commit();
            } catch (Exception e) {
                try {
                    conn.rollback();
                } catch (SQLException ignored) {
                    // ignore rollback failure
                }
                if (e instanceof DaoException) {
                    throw (DaoException) e;
                }
                throw new DaoException(e.getMessage(), e);
            } finally {
                conn.setAutoCommit(prior);
            }
        } catch (SQLException e) {
            throw new DaoException("manualAllocate transaction failed", e);
        }
    }

    public void approvePendingAllocation(int allocationId) {
        try (Connection conn = DBConnection.getConnection()) {
            boolean prior = conn.getAutoCommit();
            conn.setAutoCommit(false);
            try {
                Optional<PendingAllocationInfo> infoOpt = allocationDao.findPendingAllocation(conn, allocationId);
                if (!infoOpt.isPresent()) {
                    conn.commit();
                    return;
                }
                PendingAllocationInfo info = infoOpt.get();
                int roomId = info.getRoomId();
                int studentUserId = info.getUserId();

                if (allocationDao.hasOtherActiveAllocation(conn, studentUserId, allocationId)) {
                    throw new DaoException("Student already has another active room.");
                }
                if (!roomDao.tryIncrementOccupied(conn, roomId)) {
                    throw new DaoException("Room is full; cannot approve this request.");
                }
                allocationDao.updateAllocationStatus(conn, allocationId, "active");
                allocationDao.rejectOtherPendingForUser(conn, studentUserId, allocationId);
                roomDao.syncRoomStatus(conn, roomId);
                conn.commit();
            } catch (Exception e) {
                try {
                    conn.rollback();
                } catch (SQLException ignored) {
                    // ignore rollback failure
                }
                if (e instanceof DaoException) {
                    throw (DaoException) e;
                }
                throw new DaoException(e.getMessage(), e);
            } finally {
                conn.setAutoCommit(prior);
            }
        } catch (SQLException e) {
            throw new DaoException("approvePendingAllocation transaction failed", e);
        }
    }

    public void rejectPendingAllocation(int allocationId) {
        try (Connection conn = DBConnection.getConnection()) {
            allocationDao.rejectPending(conn, allocationId);
        } catch (SQLException e) {
            throw new DaoException("rejectPendingAllocation failed", e);
        }
    }

    public List<Room> listRoomsAvailableForStudentApplication() {
        return roomDao.findWithVacancyOrdered();
    }

    public void submitStudentRoomApplication(int userId, int roomId) {
        if (allocationDao.hasPendingOrActive(userId)) {
            throw new DaoException("You already have a pending or active allocation.");
        }
        allocationDao.insertPendingApplication(userId, roomId);
    }
}
