package modelo;

public class Fornecedor {
    private Integer idFornecedor;
    private String nome;
    private String telefone;

    public Fornecedor() {}

    public Fornecedor(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public Integer getIdFornecedor() { return idFornecedor; }
    public void setIdFornecedor(Integer idFornecedor) { this.idFornecedor = idFornecedor; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}