package model;

import java.sql.Date;
import javafx.beans.property.*;

public class Cliente {

    private transient IntegerProperty idProperty;
    private transient StringProperty nomeProperty;
    private transient StringProperty telefoneProperty;
    private transient StringProperty enderecoProperty;
    private transient ObjectProperty<Date> dataNascimentoProperty;
    private transient StringProperty loginProperty;
    private transient StringProperty senhaProperty;

    // 🔹 Construtor vazio
    public Cliente() {}

    // 🔹 Construtor com ID
    public Cliente(int id, String nome, String telefone, String endereco, Date dataNascimento, String login, String senha) {
        this.idProperty = new SimpleIntegerProperty(id);
        this.nomeProperty = new SimpleStringProperty(nome);
        this.telefoneProperty = new SimpleStringProperty(telefone);
        this.enderecoProperty = new SimpleStringProperty(endereco);
        this.dataNascimentoProperty = new SimpleObjectProperty<>(dataNascimento);
        this.loginProperty = new SimpleStringProperty(login);
        this.senhaProperty = new SimpleStringProperty(senha);
    }

    // 🔹 Construtor sem ID
    public Cliente(String nome, String telefone, String endereco, Date dataNascimento, String login, String senha) {
        this.nomeProperty = new SimpleStringProperty(nome);
        this.telefoneProperty = new SimpleStringProperty(telefone);
        this.enderecoProperty = new SimpleStringProperty(endereco);
        this.dataNascimentoProperty = new SimpleObjectProperty<>(dataNascimento);
        this.loginProperty = new SimpleStringProperty(login);
        this.senhaProperty = new SimpleStringProperty(senha);
    }

    // 🆔 ID
    public int getId() {
        return idProperty == null ? 0 : idProperty.get();
    }

    public void setId(int id) {
        if (idProperty == null) idProperty = new SimpleIntegerProperty(id);
        else idProperty.set(id);
    }

    public IntegerProperty idProperty() {
        if (idProperty == null) idProperty = new SimpleIntegerProperty(0);
        return idProperty;
    }

    // 👤 Nome
    public String getNome() {
        return nomeProperty == null ? null : nomeProperty.get();
    }

    public void setNome(String nome) {
        if (nomeProperty == null) nomeProperty = new SimpleStringProperty(nome);
        else nomeProperty.set(nome);
    }

    public StringProperty nomeProperty() {
        if (nomeProperty == null) nomeProperty = new SimpleStringProperty("");
        return nomeProperty;
    }

    // 📞 Telefone
    public String getTelefone() {
        return telefoneProperty == null ? null : telefoneProperty.get();
    }

    public void setTelefone(String telefone) {
        if (telefoneProperty == null) telefoneProperty = new SimpleStringProperty(telefone);
        else telefoneProperty.set(telefone);
    }

    public StringProperty telefoneProperty() {
        if (telefoneProperty == null) telefoneProperty = new SimpleStringProperty("");
        return telefoneProperty;
    }

    // 🏠 Endereço
    public String getEndereco() {
        return enderecoProperty == null ? null : enderecoProperty.get();
    }

    public void setEndereco(String endereco) {
        if (enderecoProperty == null) enderecoProperty = new SimpleStringProperty(endereco);
        else enderecoProperty.set(endereco);
    }

    public StringProperty enderecoProperty() {
        if (enderecoProperty == null) enderecoProperty = new SimpleStringProperty("");
        return enderecoProperty;
    }

    // 🎂 Data de nascimento
    public Date getDataNascimento() {
        return dataNascimentoProperty == null ? null : dataNascimentoProperty.get();
    }

    public void setDataNascimento(Date dataNascimento) {
        if (dataNascimentoProperty == null) dataNascimentoProperty = new SimpleObjectProperty<>(dataNascimento);
        else dataNascimentoProperty.set(dataNascimento);
    }

    public ObjectProperty<Date> dataNascimentoProperty() {
        if (dataNascimentoProperty == null) dataNascimentoProperty = new SimpleObjectProperty<>(null);
        return dataNascimentoProperty;
    }

    // 🔐 Login
    public String getLogin() {
        return loginProperty == null ? null : loginProperty.get();
    }

    public void setLogin(String login) {
        if (loginProperty == null) loginProperty = new SimpleStringProperty(login);
        else loginProperty.set(login);
    }

    public StringProperty loginProperty() {
        if (loginProperty == null) loginProperty = new SimpleStringProperty("");
        return loginProperty;
    }

    // 🔐 Senha
    public String getSenha() {
        return senhaProperty == null ? null : senhaProperty.get();
    }

    public void setSenha(String senha) {
        if (senhaProperty == null) senhaProperty = new SimpleStringProperty(senha);
        else senhaProperty.set(senha);
    }

    public StringProperty senhaProperty() {
        if (senhaProperty == null) senhaProperty = new SimpleStringProperty("");
        return senhaProperty;
    }
}