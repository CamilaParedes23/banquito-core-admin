package com.banquito.core.admin.domain.enums;

import lombok.Getter;

@Getter
public enum ResultadoAuditoriaAdminEnum {
    OK("OK"),
ERROR("ERROR"),
DENEGADO("DENEGADO");

    private final String value;

    ResultadoAuditoriaAdminEnum(String value) {
        this.value = value;
    }
}
