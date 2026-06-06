package com.banquito.core.admin.api.dto.api;

public record UserCoreResponse(
        String userCoreUuid,
        String identityUuid,
        String branchCode,
        String fullName,
        String position,
        String status
) {}
