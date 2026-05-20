package com.smarthostel.service.dto;

import com.smarthostel.model.User;

import java.util.List;

public class ManageStudentsView {
    private final List<User> students;
    private final String searchQuery;
    private final User editStudent;

    public ManageStudentsView(List<User> students, String searchQuery, User editStudent) {
        this.students = students;
        this.searchQuery = searchQuery;
        this.editStudent = editStudent;
    }

    public List<User> getStudents() {
        return students;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public User getEditStudent() {
        return editStudent;
    }
}
