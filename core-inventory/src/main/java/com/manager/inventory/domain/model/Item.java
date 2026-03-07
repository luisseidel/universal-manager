package com.manager.inventory.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Item {

    private final UUID id;
    private String nome;
    private BigDecimal preco;
    private int quantidadeEstoque;

    public Item(String nome, BigDecimal preco) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        setPreco(preco); // Validação de regra de negócio
        this.quantidadeEstoque = 0;
    }

    // Regra de Negócio: O preço nunca pode ser negativo
    public void setPreco(BigDecimal preco) {
        if (preco.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        this.preco = preco;
    }

    public void adicionarEstoque(int quantidade) {
        this.quantidadeEstoque += quantidade;
    }

    // Getters simples...
    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public BigDecimal getPreco() { return preco; }
    public int getQuantidadeEstoque() { return quantidadeEstoque; }
}