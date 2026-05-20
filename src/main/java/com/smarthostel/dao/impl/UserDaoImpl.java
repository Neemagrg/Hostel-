package com.smarthostel.dao.impl;

import com.smarthostel.dao.DaoException;
import com.smarthostel.dao.UserDao;
import com.smarthostel.dao.mapper.UserMapper;
import com.smarthostel.model.User;
import com.smarthostel.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    @Override
    public Optional<User> authenticate(String email, String password) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM users WHERE email = ? AND password = ?")) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(UserMapper.mapAuthUser(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException("authenticate failed", e);
        }
    }

    @Override
    public Optional<User> findStudentProfile(int userId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM users WHERE user_id = ? AND role = 'student'")) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(UserMapper.mapStudentProfile(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException("findStudentProfile failed", e);
        }
    }

    @Override
    public List<User> findAllStudents(String searchQuery) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT user_id, name, email, phone, role, course, year_of_study FROM users WHERE role = 'student'";
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                sql += " AND (name LIKE ? OR email LIKE ? OR phone LIKE ?)";
            }
            sql += " ORDER BY name";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                String like = "%" + searchQuery.trim() + "%";
                pstmt.setString(1, like);
                pstmt.setString(2, like);
                pstmt.setString(3, like);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                List<User> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(UserMapper.mapStudentRow(rs));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DaoException("findAllStudents failed", e);
        }
    }

    @Override
    public Optional<User> findStudentForEdit(int userId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT user_id, name, email, phone, course, year_of_study FROM users WHERE user_id=? AND role='student'")) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(UserMapper.mapStudentRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException("findStudentForEdit failed", e);
        }
    }

    @Override
    public void insertStudent(String name, String email, String phone, String password, String course, String yearOfStudy) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO users (name, email, phone, password, role, course, year_of_study) VALUES (?,?,?,?, 'student', ?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, password);
            pstmt.setString(5, course);
            pstmt.setString(6, yearOfStudy);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("insertStudent failed", e);
        }
    }

    @Override
    public void updateStudent(int userId, String name, String email, String phone, String course, String yearOfStudy) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE users SET name=?, email=?, phone=?, course=?, year_of_study=? WHERE user_id=? AND role='student'")) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, course);
            pstmt.setString(5, yearOfStudy);
            pstmt.setInt(6, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("updateStudent failed", e);
        }
    }

    @Override
    public void deleteStudent(int userId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM users WHERE user_id = ? AND role='student'")) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("deleteStudent failed", e);
        }
    }

    @Override
    public List<User> findStudentsEligibleForAllocation() {
        String sql = "SELECT u.* FROM users u WHERE u.role = 'student' "
                + "AND NOT EXISTS (SELECT 1 FROM allocations a WHERE a.user_id = u.user_id AND a.status = 'active')";
        try (Connection conn = DBConnection.getConnection();
             ResultSet rs = conn.createStatement().executeQuery(sql)) {
            List<User> students = new ArrayList<>();
            while (rs.next()) {
                students.add(UserMapper.mapEligibleAllocationRow(rs));
            }
            return students;
        } catch (SQLException e) {
            throw new DaoException("findStudentsEligibleForAllocation failed", e);
        }
    }

    @Override
    public List<User> searchStudentsByNameOrEmail(String pattern) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT user_id, name, email, phone, course, year_of_study FROM users "
                             + "WHERE role='student' AND (name LIKE ? OR email LIKE ?) ORDER BY name")) {
            String like = "%" + pattern.trim() + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            try (ResultSet rs = ps.executeQuery()) {
                List<User> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(UserMapper.mapSearchRow(rs));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DaoException("searchStudentsByNameOrEmail failed", e);
        }
    }

    @Override
    public List<User> searchStudentsByAllocatedRoom(String roomPattern) {
        String sql = "SELECT u.user_id, u.name, u.email, u.phone, u.course, u.year_of_study, r.room_number "
                + "FROM users u "
                + "JOIN allocations a ON u.user_id = a.user_id AND a.status = 'active' "
                + "JOIN rooms r ON a.room_id = r.room_id "
                + "WHERE u.role = 'student' AND r.room_number LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + roomPattern.trim() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                List<User> list = new ArrayList<>();
                while (rs.next()) {
                    User u = UserMapper.mapSearchRow(rs);
                    u.setCurrentRoom(rs.getString("room_number"));
                    list.add(u);
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DaoException("searchStudentsByAllocatedRoom failed", e);
        }
    }

    @Override
    public List<User> findStudentIdNamePairs() {
        try (Connection conn = DBConnection.getConnection();
             ResultSet rs = conn.createStatement().executeQuery(
                     "SELECT user_id, name FROM users WHERE role='student' ORDER BY name")) {
            List<User> list = new ArrayList<>();
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setName(rs.getString("name"));
                list.add(u);
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException("findStudentIdNamePairs failed", e);
        }
    }

    @Override
    public void registerStudent(String name, String email, String phone, String password, String course, String yearOfStudy) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO users (name, email, phone, password, role, course, year_of_study) "
                             + "VALUES (?, ?, ?, ?, 'student', ?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, password);
            pstmt.setString(5, course);
            pstmt.setString(6, yearOfStudy);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("registerStudent failed", e);
        }
    }
}
