package model;

import java.math.BigDecimal;
import javafx.beans.property.*;

public class Produto {

    private transient IntegerProperty idProperty;
    private transient StringProperty descricaoProperty;
    private transient ObjectProperty<BigDecimal> valorProperty;
    private transient IntegerProperty quantidadeEstoqueProperty;

    // Construtor vazio
    public Produto() {}

    // ?Construtor com ID
    public Produto(int id, String descricao, BigDecimal valor, int quantidadeEstoque) {
        this.idProperty = new SimpleIntegerProperty(id);
        this.descricaoProperty = new SimpleStringProperty(descricao);
        this.valorProperty = new SimpleObjectProperty<>(valor);
        this.quantidadeEstoqueProperty = new SimpleIntegerProperty(quantidadeEstoque);
    }

    //  Construtor sem ID
    public Produto(String descricao, BigDecimal valor, int quantidadeEstoque) {
        this.descricaoProperty = new SimpleStringProperty(descricao);
        this.valorProperty = new SimpleObjectProperty<>(valor);
        this.quantidadeEstoqueProperty = new SimpleIntegerProperty(quantidadeEstoque);
    }

    //  ID
    public int getId() {
        return idProperty == null ? 0 : idProperty.get();
    }

    public void setId(int id) {
        if (idProperty == null) {
            idProperty = new SimpleIntegerProperty(id);
        } else {
            idProperty.set(id);
        }
    }

    public IntegerProperty idProperty() {
        if (idProperty == null) {
            idProperty = new SimpleIntegerProperty(0);
        }
        return idProperty;
    }

    // Descrição
    public String getDescricao() {
        return descricaoProperty == null ? null : descricaoProperty.get();
    }

    public void setDescricao(String descricao) {
        if (descricaoProperty == null) {
            descricaoProperty = new SimpleStringProperty(descricao);
        } else {
            descricaoProperty.set(descricao);
        }
    }

    public StringProperty descricaoProperty() {
        if (descricaoProperty == null) {
            descricaoProperty = new SimpleStringProperty("");
        }
        return descricaoProperty;
    }

    //  Valor
    public BigDecimal getValor() {
        return valorProperty == null ? null : valorProperty.get();
    }

    public void setValor(BigDecimal valor) {
        if (valorProperty == null) {
            valorProperty = new SimpleObjectProperty<>(valor);
        } else {
            valorProperty.set(valor);
        }
    }

    public ObjectProperty<BigDecimal> valorProperty() {
        if (valorProperty == null) {
            valorProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
        }
        return valorProperty;
    }

    //  Estoque
    public int getQuantidadeEstoque() {
        return quantidadeEstoqueProperty == null ? 0 : quantidadeEstoqueProperty.get();
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        if (quantidadeEstoqueProperty == null) {
            quantidadeEstoqueProperty = new SimpleIntegerProperty(quantidadeEstoque);
        } else {
            quantidadeEstoqueProperty.set(quantidadeEstoque);
        }
    }

    public IntegerProperty quantidadeEstoqueProperty() {
        if (quantidadeEstoqueProperty == null) {
            quantidadeEstoqueProperty = new SimpleIntegerProperty(0);
        }
        return quantidadeEstoqueProperty;
    }
}