package com.banquito.core.admin.domain.enums;

import lombok.Getter;

@Getter
public enum DominioOperativoEnum {
    CORE("CORE"),
SWITCH("SWITCH"),
CANAL("CANAL"),
SFTP("SFTP"),
CONTABLE("CONTABLE");

    private final String value;

    DominioOperativoEnum(String value) {
        this.value = value;
    }
}
