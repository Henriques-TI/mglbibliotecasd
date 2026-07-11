package skylink.mglbiblioteca.model;

/**
 * @Henriques
 */
public enum PerfilFuncionario {
    Admin("Administrador"),
    Bibliotecario("Bibliotecário"),
    Atendente("Atendente");

    private final String descricao;

    PerfilFuncionario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}