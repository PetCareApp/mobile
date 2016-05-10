package cap7.com.br.petcare.model;

import java.io.Serializable;

/**
 * Created by Virginia on 28/02/2016.
 */
public class Animal implements Serializable {

    private Integer id;

    private String codigo;

    private String nome;

    private String nascimento;

    private Especie especie;

    private Sexo sexo;

    private String raca;

    private String cor;

    private Proprietario proprietario;

    private String foto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getEspecie() {
        return especie != null ? especie.toString() : "";
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public String getSexo() {
        return sexo != null ? sexo.toString() : "";
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public enum Especie {
        CACHORRO("Cachorro"),
        GATO("Gato");

        private String nome;
        private Especie(String nome) {
            this.nome = nome;
        }

        @Override
        public String toString() {
            return nome;
        }
    }

    public enum Sexo {
        FEMEA("Fêmea"),
        MACHO("Macho");

        private String nome;
        private Sexo(String nome) {
            this.nome = nome;
        }

        @Override
        public String toString() {
            return nome;
        }
    }


}
