package com.smarthostel.dao;

import com.smarthostel.model.Fee;

import java.util.List;
import java.util.Optional;

public interface FeeDao {

    List<Fee> findAllWithStudentNames();

    Optional<Fee> findByUserId(int userId);

    void upsert(int userId, double totalAmount, double paidAmount, double dueAmount, String status);
}
