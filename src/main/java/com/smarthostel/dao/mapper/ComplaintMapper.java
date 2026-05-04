package com.smarthostel.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ComplaintMapper {

    private ComplaintMapper() {}

    public static Complaint mapRow(ResultSet rs) throws SQLException {
        Complaint c = new Complaint();
        c.setComplaintId(rs.getInt("complaint_id"));
        c.setUserId(rs.getInt("user_id"));
        c.setDescription(rs.getString("description"));
        c.setStatus(rs.getString("status"));
        c.setCreatedAt(rs.getTimestamp("created_at"));
        try {
            c.setCategory(rs.getString("category"));
        } catch (SQLException e) {
            c.setCategory("other");
        }
        try {
            c.setUserName(rs.getString("user_name"));
        } catch (SQLException ignored) {
        }
        return c;
    }
}
