package com.banquito.core.admin.domain.enums;

import lombok.Getter;

@Getter
public enum AccionDespuesCorteEnum {
    RECHAZAR("RECHAZAR"),
ENCOLAR("ENCOLAR"),
SIGUIENTE_DIA_HABIL("SIGUIENTE_DIA_HABIL"),
PERMITIR("PERMITIR");

    private final String value;

    AccionDespuesCorteEnum(String value) {
        this.value = value;
    }
}
