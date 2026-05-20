package com.smarthostel.service;

import com.smarthostel.dao.DaoFactory;

public final class ServiceFactory {

    private static final AuthService AUTH_SERVICE =
            new AuthService(DaoFactory.getUserDao());
    private static final StudentManagementService STUDENT_MANAGEMENT_SERVICE =
            new StudentManagementService(DaoFactory.getUserDao());
    private static final RoomAllocationService ROOM_ALLOCATION_SERVICE =
            new RoomAllocationService(DaoFactory.getRoomDao(), DaoFactory.getAllocationDao(), DaoFactory.getUserDao());
    private static final FeeService FEE_SERVICE =
            new FeeService(DaoFactory.getFeeDao(), DaoFactory.getUserDao());
    private static final ComplaintService COMPLAINT_SERVICE =
            new ComplaintService(DaoFactory.getComplaintDao());
    private static final VisitorService VISITOR_SERVICE =
            new VisitorService(DaoFactory.getVisitorDao());
    private static final DashboardService DASHBOARD_SERVICE =
            new DashboardService(DaoFactory.getDashboardDao());
    private static final SearchService SEARCH_SERVICE =
            new SearchService(DaoFactory.getUserDao(), DaoFactory.getRoomDao());
    private static final StudentPortalService STUDENT_PORTAL_SERVICE =
            new StudentPortalService(DaoFactory.getUserDao(), DaoFactory.getAllocationDao(), DaoFactory.getFeeDao());

    private ServiceFactory() {}

    public static AuthService getAuthService() {
        return AUTH_SERVICE;
    }

    public static StudentManagementService getStudentManagementService() {
        return STUDENT_MANAGEMENT_SERVICE;
    }

    public static RoomAllocationService getRoomAllocationService() {
        return ROOM_ALLOCATION_SERVICE;
    }

    public static FeeService getFeeService() {
        return FEE_SERVICE;
    }

    public static ComplaintService getComplaintService() {
        return COMPLAINT_SERVICE;
    }

    public static VisitorService getVisitorService() {
        return VISITOR_SERVICE;
    }

    public static DashboardService getDashboardService() {
        return DASHBOARD_SERVICE;
    }

    public static SearchService getSearchService() {
        return SEARCH_SERVICE;
    }

    public static StudentPortalService getStudentPortalService() {
        return STUDENT_PORTAL_SERVICE;
    }
}
