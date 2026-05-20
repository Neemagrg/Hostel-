package com.smarthostel.dao;

import com.smarthostel.dao.impl.AllocationDaoImpl;
import com.smarthostel.dao.impl.ComplaintDaoImpl;
import com.smarthostel.dao.impl.DashboardDaoImpl;
import com.smarthostel.dao.impl.FeeDaoImpl;
import com.smarthostel.dao.impl.RoomDaoImpl;
import com.smarthostel.dao.impl.UserDaoImpl;
import com.smarthostel.dao.impl.VisitorDaoImpl;

public final class DaoFactory {

    private static final UserDao USER_DAO = new UserDaoImpl();
    private static final RoomDao ROOM_DAO = new RoomDaoImpl();
    private static final AllocationDao ALLOCATION_DAO = new AllocationDaoImpl();
    private static final FeeDao FEE_DAO = new FeeDaoImpl();
    private static final ComplaintDao COMPLAINT_DAO = new ComplaintDaoImpl();
    private static final VisitorDao VISITOR_DAO = new VisitorDaoImpl();
    private static final DashboardDao DASHBOARD_DAO = new DashboardDaoImpl();

    private DaoFactory() {}

    public static UserDao getUserDao() {
        return USER_DAO;
    }

    public static RoomDao getRoomDao() {
        return ROOM_DAO;
    }

    public static AllocationDao getAllocationDao() {
        return ALLOCATION_DAO;
    }

    public static FeeDao getFeeDao() {
        return FEE_DAO;
    }

    public static ComplaintDao getComplaintDao() {
        return COMPLAINT_DAO;
    }

    public static VisitorDao getVisitorDao() {
        return VISITOR_DAO;
    }

    public static DashboardDao getDashboardDao() {
        return DASHBOARD_DAO;
    }
}
