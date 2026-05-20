package com.smarthostel.dao.mapper;

import com.smarthostel.model.Fee;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class FeeMapper {

    private FeeMapper() {}

    public static Fee mapRow(ResultSet rs) throws SQLException {
        Fee f = new Fee();
        f.setFeeId(rs.getInt("fee_id"));
        f.setUserId(rs.getInt("user_id"));
        f.setTotalAmount(rs.getDouble("total_amount"));
        f.setPaidAmount(rs.getDouble("paid_amount"));
        f.setDueAmount(rs.getDouble("due_amount"));
        f.setStatus(rs.getString("status"));
        try {
            f.setUserName(rs.getString("user_name"));
        } catch (SQLException ignored) {
            // joined column optional
        }
        return f;
    }
}
