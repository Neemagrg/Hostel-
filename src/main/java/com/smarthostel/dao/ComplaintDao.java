package com.smarthostel.dao;

import com.smarthostel.model.Complaint;

import java.util.List;

public interface ComplaintDao {

    List<Complaint> findAllWithStudentNames();

    List<Complaint> findForStudent(int userId);

    void insert(int userId, String category, String description);

    void updateStatus(int complaintId, String status);
}
