package skylink.mglbiblioteca.model;

public enum TipoOrigem {
    Reserva_Sala("Reserva de Sala"),
    Servico_Impressao("Serviço de Impressão"),
    Venda("Venda");

    private final String descricao;

    TipoOrigem(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
