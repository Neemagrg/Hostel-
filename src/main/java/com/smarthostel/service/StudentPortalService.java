package com.smarthostel.service;

import com.smarthostel.dao.AllocationDao;
import com.smarthostel.dao.FeeDao;
import com.smarthostel.dao.UserDao;
import com.smarthostel.model.Fee;
import com.smarthostel.model.User;
import com.smarthostel.service.dto.StudentDashboardSummary;

import java.util.Optional;

public class StudentPortalService {

    private final UserDao userDao;
    private final AllocationDao allocationDao;
    private final FeeDao feeDao;

    public StudentPortalService(UserDao userDao, AllocationDao allocationDao, FeeDao feeDao) {
        this.userDao = userDao;
        this.allocationDao = allocationDao;
        this.feeDao = feeDao;
    }

    public StudentDashboardSummary loadDashboardSummary(int userId) {
        String roomLabel = allocationDao.findActiveRoomNumberForStudent(userId).orElse("Not assigned");

        String feeStatus = "No fee record";
        double dueAmount = 0.0;
        Optional<Fee> feeOpt = feeDao.findByUserId(userId);
        if (feeOpt.isPresent()) {
            Fee f = feeOpt.get();
            feeStatus = f.getStatus();
            dueAmount = f.getDueAmount();
        }
        return new StudentDashboardSummary(roomLabel, feeStatus, dueAmount);
    }

    public Optional<User> loadStudentProfile(int userId) {
        return userDao.findStudentProfile(userId);
    }

    public String loadAllocatedRoomLabel(int userId) {
        return allocationDao.findActiveRoomNumberForStudent(userId).orElse("Not assigned");
    }

    public Optional<Fee> loadFeeForStudent(int userId) {
        return feeDao.findByUserId(userId);
    }
}
