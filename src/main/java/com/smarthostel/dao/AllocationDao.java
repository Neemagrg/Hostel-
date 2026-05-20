package com.smarthostel.dao;

import com.smarthostel.dao.dto.PendingAllocationInfo;
import com.smarthostel.model.Allocation;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface AllocationDao {

    List<Allocation> findPendingRequestsWithDetails();

    boolean hasPendingOrActive(int userId);

    void insertPendingApplication(int userId, int roomId);

    Optional<String> findActiveRoomNumberForStudent(int userId);

    boolean hasActiveAllocation(Connection conn, int userId);

    boolean hasOtherActiveAllocation(Connection conn, int userId, int excludeAllocationId);

    void deletePendingForUser(Connection conn, int userId);

    void insertActive(Connection conn, int userId, int roomId);

    Optional<PendingAllocationInfo> findPendingAllocation(Connection conn, int allocationId);

    void updateAllocationStatus(Connection conn, int allocationId, String status);

    void rejectOtherPendingForUser(Connection conn, int userId, int exceptAllocationId);

    void rejectPending(Connection conn, int allocationId);
}
