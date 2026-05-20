package com.smarthostel.service.dto;

import com.smarthostel.model.Fee;
import com.smarthostel.model.User;

import java.util.List;

public class FeeManageView {
    private final List<Fee> fees;
    private final List<User> studentOptions;

    public FeeManageView(List<Fee> fees, List<User> studentOptions) {
        this.fees = fees;
        this.studentOptions = studentOptions;
    }

    public List<Fee> getFees() {
        return fees;
    }

    public List<User> getStudentOptions() {
        return studentOptions;
    }
}
