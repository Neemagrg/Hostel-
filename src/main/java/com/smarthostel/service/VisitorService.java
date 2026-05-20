package com.smarthostel.service;

import com.smarthostel.dao.VisitorDao;
import com.smarthostel.model.Visitor;

import java.util.List;

public class VisitorService {

    private final VisitorDao visitorDao;

    public VisitorService(VisitorDao visitorDao) {
        this.visitorDao = visitorDao;
    }

    public List<Visitor> listAllForAdmin() {
        return visitorDao.findAllWithStudentNames();
    }

    public List<Visitor> listForStudent(int userId) {
        return visitorDao.findForStudent(userId);
    }

    public void submitVisitorRequest(int userId, String visitorName, String visitDate, String purpose) {
        visitorDao.insert(userId, visitorName, visitDate, purpose);
    }

    public void updateVisitorStatus(int visitorId, String status) {
        visitorDao.updateStatus(visitorId, status);
    }
}
