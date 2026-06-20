package com.banquito.core.admin.api.dto.api;

import java.util.List;

public record CoreUserListResponse(
        long total,
        int page,
        int size,
        int totalPages,
        List<UserCoreResponse> users
) {}
