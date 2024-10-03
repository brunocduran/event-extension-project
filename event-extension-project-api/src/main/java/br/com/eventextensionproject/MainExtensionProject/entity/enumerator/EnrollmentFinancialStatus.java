package br.com.eventextensionproject.MainExtensionProject.entity.enumerator;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EnrollmentFinancialStatus {
    ABERTO(0, "Aberto"),
    PAGO(1, "Pago");

    private Integer codigo;
    private String descricao;

    @JsonValue
    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    private EnrollmentFinancialStatus(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}
