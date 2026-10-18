package br.com.projeto.api_vendas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompraRequest {

    @JsonProperty("produto_id")
    private Long produtoId;

    @JsonProperty("qtde_comprada")
    private Integer qtdeComprada;

    private PagamentoRequest.Cartao cartao;

    public CompraRequest() {
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQtdeComprada() {
        return qtdeComprada;
    }

    public void setQtdeComprada(Integer qtdeComprada) {
        this.qtdeComprada = qtdeComprada;
    }

    public PagamentoRequest.Cartao getCartao() {
        return cartao;
    }

    public void setCartao(PagamentoRequest.Cartao cartao) {
        this.cartao = cartao;
    }
}