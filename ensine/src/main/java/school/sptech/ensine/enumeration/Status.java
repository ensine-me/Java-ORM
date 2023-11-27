package school.sptech.ensine.enumeration;

public enum Status {
    SOLICITADO("Solicitado"),
    AGUARDANDO_PAGAMENTO("Aguardando Pagamento"),
    AGENDADO("Agendado"),
    EM_PROGRESSO("Em Progresso"),
    CONCLUIDA("Concluída");

    @Override
    public String toString() {
        return displayName;
    }

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
