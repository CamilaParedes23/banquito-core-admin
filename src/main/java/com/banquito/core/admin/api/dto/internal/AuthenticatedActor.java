package com.banquito.core.admin.api.dto.internal;

import java.util.List;

public record AuthenticatedActor(
        String subject,
        String username,
        String actorType,
        List<String> roles,
        List<String> scopes
) {}
