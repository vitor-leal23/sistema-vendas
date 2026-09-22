package modelo;
public class Cliente {
    private Integer idCliente;
    private String nome;
    private String email;
    private Integer pontosFidelidade; // Wrapper Integer permite null!
    public Cliente() {}
    // Getters e Setters
    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getPontosFidelidade() { return pontosFidelidade; }
    public void setPontosFidelidade(Integer pontosFidelidade) { this.pontosFidelidade =
            pontosFidelidade; }
}