package com.banquito.core.admin.api.dto.api;

import jakarta.validation.constraints.NotBlank;

public record UserCoreRequest(
        @NotBlank String identityUuid,
        String branchCode,
        @NotBlank String fullName,
        String position,
        String status
) {}