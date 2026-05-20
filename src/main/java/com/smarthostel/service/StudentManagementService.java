package com.smarthostel.service;

import com.smarthostel.dao.UserDao;
import com.smarthostel.model.User;
import com.smarthostel.service.dto.ManageStudentsView;

import java.util.Optional;

public class StudentManagementService {

    private final UserDao userDao;

    public StudentManagementService(UserDao userDao) {
        this.userDao = userDao;
    }

    public ManageStudentsView loadManageStudentsPage(String searchQuery, String editUserIdParam) {
        User editStudent = null;
        if (editUserIdParam != null && !editUserIdParam.trim().isEmpty()) {
            try {
                int eid = Integer.parseInt(editUserIdParam.trim());
                Optional<User> loaded = userDao.findStudentForEdit(eid);
                if (loaded.isPresent()) {
                    editStudent = loaded.get();
                }
            } catch (NumberFormatException ignored) {
                // ignore invalid editUserId
            }
        }
        return new ManageStudentsView(
                userDao.findAllStudents(searchQuery),
                searchQuery == null ? "" : searchQuery,
                editStudent);
    }

    public void addStudent(String name, String email, String phone, String password, String course, String yearOfStudy) {
        userDao.insertStudent(name, email, phone, password, emptyToNull(course), emptyToNull(yearOfStudy));
    }

    public void updateStudent(int userId, String name, String email, String phone, String course, String yearOfStudy) {
        userDao.updateStudent(userId, name, email, phone, emptyToNull(course), emptyToNull(yearOfStudy));
    }

    public void deleteStudent(int userId) {
        userDao.deleteStudent(userId);
    }

    public void register(String name, String email, String phone, String password, String course, String yearOfStudy) {
        userDao.registerStudent(name, email, phone, password, emptyToNull(course), emptyToNull(yearOfStudy));
    }

    private static String emptyToNull(String s) {
        return (s == null || s.trim().isEmpty()) ? null : s.trim();
    }
}
