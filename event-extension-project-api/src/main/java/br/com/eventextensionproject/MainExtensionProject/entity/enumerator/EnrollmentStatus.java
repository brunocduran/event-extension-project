package br.com.eventextensionproject.MainExtensionProject.entity.enumerator;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EnrollmentStatus {
    CANCELADA(0, "Cancelada"),
    ATIVO(1, "Ativo");

    private Integer codigo;
    private String descricao;

    @JsonValue
    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    private EnrollmentStatus(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static EnrollmentStatus parse(Integer codigo) {
        if(codigo != null) {
            for(EnrollmentStatus se : EnrollmentStatus.values()) {
                if(se.codigo.equals(codigo)) {
                    return se;
                }
            }
        }
        return null;
    }
}
