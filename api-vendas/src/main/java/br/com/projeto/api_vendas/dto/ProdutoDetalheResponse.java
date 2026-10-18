package br.com.projeto.api_vendas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProdutoDetalheResponse {

    private Long id;

    private String nome;

    @JsonProperty("valor_unitario")
    private Double valorUnitario;

    @JsonProperty("qtde_estoque")
    private Integer qtdeEstoque;

    @JsonProperty("data_ultima_venda")
    private String dataUltimaVenda;

    @JsonProperty("valor_ultima_venda")
    private Double valorUltimaVenda;

    public ProdutoDetalheResponse() {
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

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Integer getQtdeEstoque() {
        return qtdeEstoque;
    }

    public void setQtdeEstoque(Integer qtdeEstoque) {
        this.qtdeEstoque = qtdeEstoque;
    }

    public String getDataUltimaVenda() {
        return dataUltimaVenda;
    }

    public void setDataUltimaVenda(String dataUltimaVenda) {
        this.dataUltimaVenda = dataUltimaVenda;
    }

    public Double getValorUltimaVenda() {
        return valorUltimaVenda;
    }

    public void setValorUltimaVenda(Double valorUltimaVenda) {
        this.valorUltimaVenda = valorUltimaVenda;
    }
}