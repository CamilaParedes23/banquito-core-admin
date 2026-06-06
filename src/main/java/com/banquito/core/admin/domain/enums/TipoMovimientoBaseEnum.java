package com.banquito.core.admin.domain.enums;

import lombok.Getter;

@Getter
public enum TipoMovimientoBaseEnum {
    DEBITO("DEBITO"),
CREDITO("CREDITO");

    private final String value;

    TipoMovimientoBaseEnum(String value) {
        this.value = value;
    }
}
