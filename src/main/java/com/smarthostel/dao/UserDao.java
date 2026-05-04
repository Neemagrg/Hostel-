package com.smarthostel.dao;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> authenticate(String email, String password);

    Optional<User> findStudentProfile(int userId);

    List<User> findAllStudents(String searchQuery);

    Optional<User> findStudentForEdit(int userId);

    void insertStudent(String name, String email, String phone, String password, String course, String yearOfStudy);

    void updateStudent(int userId, String name, String email, String phone, String course, String yearOfStudy);

    void deleteStudent(int userId);

    /** Students without an active allocation (for manual room assignment dropdown). */
    List<User> findStudentsEligibleForAllocation();

    List<User> searchStudentsByNameOrEmail(String pattern);

    List<User> searchStudentsByAllocatedRoom(String roomPattern);

    /** Minimal id + name rows for fee dropdowns. */
    List<User> findStudentIdNamePairs();

    void registerStudent(String name, String email, String phone, String password, String course, String yearOfStudy);
}
