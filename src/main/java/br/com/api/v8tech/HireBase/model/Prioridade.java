package br.com.api.v8tech.HireBase.model;

public enum Prioridade {
    ALTA,
    MEDIA,
    BAIXA;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
