package com.banquito.core.admin.api.dto.api;

public record MetricsResponse(
        long totalBranches,
        long activeBranches,
        long totalHolidays,
        long activeHolidays,
        long totalParameters,
        long activeParameters,
        long totalOperationalWindows,
        long activeOperationalWindows,
        long totalFinancialInstitutions,
        long activeFinancialInstitutions,
        long totalAccountSubtypes,
        long activeAccountSubtypes,
        long totalTransactionSubtypes,
        long activeTransactionSubtypes,
        long totalCoreUsers,
        long activeCoreUsers,
        long totalAuditEvents,
        long pendingOutboxEvents
) {}
