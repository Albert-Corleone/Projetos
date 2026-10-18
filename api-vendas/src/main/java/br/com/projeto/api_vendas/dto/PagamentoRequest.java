package br.com.projeto.api_vendas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PagamentoRequest {

    private Double valor;
    private Cartao cartao;

    public PagamentoRequest() {
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public static class Cartao {

        private String titular;
        private String numero;
        @JsonProperty("data_expiracao")
        private String dataExpiracao;
        private String bandeira;
        private String cvv;

        public Cartao() {
        }

        public String getTitular() {
            return titular;
        }

        public void setTitular(String titular) {
            this.titular = titular;
        }

        public String getNumero() {
            return numero;
        }

        public void setNumero(String numero) {
            this.numero = numero;
        }

        public String getDataExpiracao() {
            return dataExpiracao;
        }

        public void setDataExpiracao(String dataExpiracao) {
            this.dataExpiracao = dataExpiracao;
        }

        public String getBandeira() {
            return bandeira;
        }

        public void setBandeira(String bandeira) {
            this.bandeira = bandeira;
        }

        public String getCvv() {
            return cvv;
        }

        public void setCvv(String cvv) {
            this.cvv = cvv;
        }
    }
}