package com.smarthostel.dao;

import com.smarthostel.model.Visitor;

import java.util.List;

public interface VisitorDao {

    List<Visitor> findAllWithStudentNames();

    List<Visitor> findForStudent(int userId);

    void insert(int userId, String visitorName, String visitDate, String purpose);

    void updateStatus(int visitorId, String status);
}
