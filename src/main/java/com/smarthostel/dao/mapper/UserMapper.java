package com.smarthostel.dao.mapper;

import com.smarthostel.model.User;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public final class UserMapper {

    private UserMapper() {}

    public static User mapStudentRow(ResultSet rs) throws SQLException {
        User student = new User();
        student.setUserId(rs.getInt("user_id"));
        student.setName(rs.getString("name"));
        student.setEmail(rs.getString("email"));
        student.setPhone(rs.getString("phone"));
        student.setCourse(safeString(rs, "course"));
        student.setYearOfStudy(safeString(rs, "year_of_study"));
        return student;
    }

    public static User mapAuthUser(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        boolean hasCourse = columnExists(md, "course");
        boolean hasYear = columnExists(md, "year_of_study");

        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setRole(rs.getString("role"));
        user.setCourse(hasCourse ? rs.getString("course") : null);
        user.setYearOfStudy(hasYear ? rs.getString("year_of_study") : null);
        return user;
    }

    public static User mapSearchRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserId(rs.getInt("user_id"));
        u.setName(rs.getString("name"));
        u.setEmail(rs.getString("email"));
        u.setPhone(rs.getString("phone"));
        u.setCourse(safeString(rs, "course"));
        u.setYearOfStudy(safeString(rs, "year_of_study"));
        return u;
    }

    /** Profile page: works with SELECT * even when optional columns are absent from schema. */
    public static User mapStudentProfile(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        User u = new User();
        u.setUserId(rs.getInt("user_id"));
        u.setName(rs.getString("name"));
        u.setEmail(rs.getString("email"));
        u.setPhone(rs.getString("phone"));
        u.setCourse(columnExists(md, "course") ? rs.getString("course") : null);
        u.setYearOfStudy(columnExists(md, "year_of_study") ? rs.getString("year_of_study") : null);
        return u;
    }

    public static User mapEligibleAllocationRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserId(rs.getInt("user_id"));
        u.setName(rs.getString("name"));
        u.setEmail(rs.getString("email"));
        u.setPhone(rs.getString("phone"));
        u.setRole(rs.getString("role"));
        u.setCourse(safeString(rs, "course"));
        u.setYearOfStudy(safeString(rs, "year_of_study"));
        return u;
    }

    private static boolean columnExists(ResultSetMetaData md, String name) throws SQLException {
        for (int i = 1; i <= md.getColumnCount(); i++) {
            if (name.equalsIgnoreCase(md.getColumnLabel(i))) {
                return true;
            }
        }
        return false;
    }

    private static String safeString(ResultSet rs, String column) {
        try {
            return rs.getString(column);
        } catch (SQLException e) {
            return null;
        }
    }
}
