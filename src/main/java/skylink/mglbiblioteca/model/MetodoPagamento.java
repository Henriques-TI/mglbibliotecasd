package skylink.mglbiblioteca.model;

public enum MetodoPagamento {
    Cash("Dinheiro (Cash)"),
    TPA("TPA"),
    Transferencia("Transferência"); 

    private final String descricao;

    MetodoPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}