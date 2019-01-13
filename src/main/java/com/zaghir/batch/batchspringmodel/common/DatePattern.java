package com.zaghir.batch.batchspringmodel.common;

public enum DatePattern {

    ANNEE_MOIS_JOUR("yyyy-MM-dd"), ANNEEMOISJOUR("yyyyMMdd"), JOUR_MOIS_ANNEE("dd/MM/yyyy"), JOURMOISANNEEHHMMSS("yyyyMMddHHmmss"), DDMMYYYY("dd/mm/yyyy");

    private String pattern;

    private DatePattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return this.pattern;
    }

}
