package br.com.eventextensionproject.MainExtensionProject.entity.enumerator;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AttendanceStatus {
    AUSENTE(0, "Ausente"),
    PRESENTE(1, "Presente");

    private Integer codigo;
    private String descricao;

    @JsonValue
    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    private AttendanceStatus(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}
