package br.com.projeto.api_vendas.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.api_vendas.dto.PagamentoRequest;

@RestController
@RequestMapping("/api/pagamento")
public class PagamentoController {

    @PostMapping("/compras")
    public ResponseEntity<Map<String, Object>> realizarPagamento(
            @RequestBody PagamentoRequest pagamento) {

        Map<String, Object> resposta = new HashMap<>();

        if (pagamento.getValor() > 100) {
            resposta.put("valor", pagamento.getValor());
            resposta.put("estado", "APROVADO");
        } else {
            resposta.put("valor", pagamento.getValor());
            resposta.put("estado", "REJEITADO");
        }

        return ResponseEntity.ok(resposta);
    }
}