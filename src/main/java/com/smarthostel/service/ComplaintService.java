package com.smarthostel.service;

import com.smarthostel.dao.ComplaintDao;
import com.smarthostel.model.Complaint;

import java.util.List;

public class ComplaintService {

    private final ComplaintDao complaintDao;

    public ComplaintService(ComplaintDao complaintDao) {
        this.complaintDao = complaintDao;
    }

    public List<Complaint> listAllForAdmin() {
        return complaintDao.findAllWithStudentNames();
    }

    public List<Complaint> listForStudent(int userId) {
        return complaintDao.findForStudent(userId);
    }

    public void submitComplaint(int userId, String category, String description) {
        complaintDao.insert(userId, category, description);
    }

    public void updateComplaintStatus(int complaintId, String status) {
        complaintDao.updateStatus(complaintId, status);
    }
}
