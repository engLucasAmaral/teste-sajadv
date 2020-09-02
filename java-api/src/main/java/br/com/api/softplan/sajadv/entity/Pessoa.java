package br.com.api.softplan.sajadv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author lucas
 */
@Entity
@Table(name = "tb_pessoa")
@NamedQueries({
    @NamedQuery(name = "Pessoa.findAll",
            query = "SELECT t FROM Pessoa t where t.ativo = true"),
    @NamedQuery(name = "Pessoa.buscarPorCPF",
            query = "SELECT t FROM Pessoa t where t.cpf = :p0 and t.ativo = true")})

public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "cpf", nullable = false/*, unique = true*/)
    private String cpf;

    @Column(name = "nascimento", nullable = false)
    @Temporal(TemporalType.DATE)
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date nascimento;

    @Column(name = "email", nullable = false, length = 400)
    private String email;

    @Column(name = "image", nullable = true)
    private byte[] image;

//    @JsonIgnore
    @Column(name = "data_cadastro", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private Date dataCadastro = new Date();

    @JsonIgnore
    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;

    public Pessoa() {
    }

    public Pessoa(Long secId) {
        this.id = secId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Pessoa{" + "id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", nascimento=" + nascimento + ", email=" + email + ", image=" + image + ", ativo=" + ativo + '}';
    }

}
