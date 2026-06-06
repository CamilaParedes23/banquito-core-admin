package com.banquito.core.admin.api.dto.api;

import java.time.LocalDateTime;

public record BranchResponse(
        String branchUuid,
        String code,
        String name,
        String city,
        String address,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
