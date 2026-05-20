package com.smarthostel.dao.mapper;

import com.smarthostel.model.Visitor;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class VisitorMapper {

    private VisitorMapper() {}

    public static Visitor mapRow(ResultSet rs) throws SQLException {
        Visitor v = new Visitor();
        v.setVisitorId(rs.getInt("visitor_id"));
        v.setUserId(rs.getInt("user_id"));
        v.setVisitorName(rs.getString("visitor_name"));
        v.setVisitDate(rs.getDate("visit_date"));
        v.setPurpose(rs.getString("purpose"));
        v.setStatus(rs.getString("status"));
        try {
            v.setStudentName(rs.getString("student_name"));
        } catch (SQLException ignored) {
        }
        return v;
    }
}
