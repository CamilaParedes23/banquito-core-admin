package com.banquito.core.admin.api.dto.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BranchRequest(
        @NotBlank @Size(max = 10) String code,
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Size(max = 80) String city,
        @Size(max = 300) String address
) {}
