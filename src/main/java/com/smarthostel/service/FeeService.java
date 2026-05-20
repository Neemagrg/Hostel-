package com.smarthostel.service;

import com.smarthostel.dao.FeeDao;
import com.smarthostel.dao.UserDao;
import com.smarthostel.service.dto.FeeManageView;

public class FeeService {

    private final FeeDao feeDao;
    private final UserDao userDao;

    public FeeService(FeeDao feeDao, UserDao userDao) {
        this.feeDao = feeDao;
        this.userDao = userDao;
    }

    public FeeManageView loadFeeManagementPage() {
        return new FeeManageView(feeDao.findAllWithStudentNames(), userDao.findStudentIdNamePairs());
    }

    public void upsertFeeRecord(int userId, double totalAmount, double paidAmount) {
        double due = totalAmount - paidAmount;
        String status = (due <= 0) ? "paid" : (paidAmount > 0 ? "partial" : "unpaid");
        feeDao.upsert(userId, totalAmount, paidAmount, Math.max(due, 0), status);
    }
}
