package br.com.projeto.api_vendas.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import br.com.projeto.api_vendas.dto.CompraRequest;
import br.com.projeto.api_vendas.dto.PagamentoRequest;
import br.com.projeto.api_vendas.model.Produto;
import br.com.projeto.api_vendas.model.Venda;
import br.com.projeto.api_vendas.repository.ProdutoRepository;
import br.com.projeto.api_vendas.repository.VendaRepository;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    private final ProdutoRepository produtoRepository;
    private final VendaRepository vendaRepository;
    private final RestClient restClient;

    public CompraController(
            ProdutoRepository produtoRepository,
            VendaRepository vendaRepository) {

        this.produtoRepository = produtoRepository;
        this.vendaRepository = vendaRepository;
        this.restClient = RestClient.create();
    }

    @PostMapping
    public ResponseEntity<?> realizarCompra(
            @RequestBody CompraRequest compra) {

        Produto produto = produtoRepository.findById(compra.getProdutoId())
                .orElse(null);

        if (produto == null) {
            return ResponseEntity.badRequest()
                    .body("Produto não encontrado");
        }

        if (compra.getQtdeComprada() == null ||
                compra.getQtdeComprada() <= 0) {

            return ResponseEntity.status(412)
                    .body("Quantidade inválida");
        }

        if (produto.getQtdeEstoque() < compra.getQtdeComprada()) {
            return ResponseEntity.status(412)
                    .body("Quantidade em estoque insuficiente");
        }

        if (compra.getCartao() == null ||
        compra.getCartao().getNumero() == null ||
        compra.getCartao().getNumero().isBlank()) {

    return ResponseEntity.status(412)
            .body("Cartão inválido");
}

if (!numeroCartaoValido(compra.getCartao().getNumero())) {
    return ResponseEntity.status(412)
            .body("Número do cartão inválido");
}

        Double valorTotal =
                produto.getValorUnitario() * compra.getQtdeComprada();

        PagamentoRequest pagamento = new PagamentoRequest();
        pagamento.setValor(valorTotal);
        pagamento.setCartao(compra.getCartao());

        Map<String, Object> respostaPagamento;

        try {
            respostaPagamento = restClient.post()
                    .uri("http://localhost:8080/api/pagamento/compras")
                    .body(pagamento)
                    .retrieve()
                    .body(Map.class);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Erro ao processar pagamento");
        }

        if (respostaPagamento == null ||
                !"APROVADO".equals(respostaPagamento.get("estado"))) {

            return ResponseEntity.badRequest()
                    .body("Pagamento rejeitado");
        }

        produto.setQtdeEstoque(
                produto.getQtdeEstoque() - compra.getQtdeComprada()
        );

        produtoRepository.save(produto);

        Venda venda = new Venda();
        venda.setProduto(produto);
        venda.setQtdeComprada(compra.getQtdeComprada());
        venda.setValorTotal(valorTotal);
        venda.setDataVenda(LocalDateTime.now());

        vendaRepository.save(venda);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Venda realizada com sucesso");
        resposta.put("valor", valorTotal);

        return ResponseEntity.ok(resposta);
    }

    private boolean numeroCartaoValido(String numero) {

    numero = numero.replaceAll("\\s+", "");

    if (!numero.matches("\\d+")) {
        return false;
    }

    int soma = 0;
    boolean dobrar = false;

    for (int i = numero.length() - 1; i >= 0; i--) {

        int digito = Character.getNumericValue(numero.charAt(i));

        if (dobrar) {
            digito *= 2;

            if (digito > 9) {
                digito -= 9;
            }
        }

        soma += digito;
        dobrar = !dobrar;
    }

    return soma % 10 == 0;
}

}