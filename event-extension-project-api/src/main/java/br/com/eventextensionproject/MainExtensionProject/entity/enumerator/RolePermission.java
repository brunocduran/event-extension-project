package br.com.eventextensionproject.MainExtensionProject.entity.enumerator;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RolePermission {

    ADMINISTRATOR(0, "ROLE_ADMINISTRATOR"),
    ORGANIZER(1, "ROLE_ORGANIZER"),
    SPONSOR(2, "ROLE_SPONSOR"),
    SUPPLIER(3, "ROLE_SUPPLIER"),
    PARTICIPANT(4, "ROLE_PARTICIPANT");

    private Integer codigo;
    private String descricao;

    RolePermission(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    @JsonValue
    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static RolePermission toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        for (RolePermission x : RolePermission.values()) {
            if (cod.equals(x.getCodigo())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Permissão inválida");
    }

}
