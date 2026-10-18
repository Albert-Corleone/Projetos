package br.com.projeto.api_vendas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.api_vendas.dto.ProdutoDetalheResponse;
import br.com.projeto.api_vendas.model.Produto;
import br.com.projeto.api_vendas.model.Venda;
import br.com.projeto.api_vendas.repository.ProdutoRepository;
import br.com.projeto.api_vendas.repository.VendaRepository;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final VendaRepository vendaRepository;

    public ProdutoController(
        ProdutoRepository produtoRepository,
        VendaRepository vendaRepository) {

    this.produtoRepository = produtoRepository;
    this.vendaRepository = vendaRepository;
}

    @PostMapping
public ResponseEntity<?> cadastrar(@RequestBody Produto produto) {

    if (produto.getNome() == null ||
            produto.getNome().isBlank()) {

        return ResponseEntity.status(412)
                .body("Nome do produto inválido");
    }

    if (produto.getValorUnitario() == null ||
            produto.getValorUnitario() <= 0) {

        return ResponseEntity.status(412)
                .body("Valor unitário inválido");
    }

    if (produto.getQtdeEstoque() == null ||
            produto.getQtdeEstoque() < 0) {

        return ResponseEntity.status(412)
                .body("Quantidade em estoque inválida");
    }

    Produto produtoSalvo = produtoRepository.save(produto);

    return ResponseEntity.ok(produtoSalvo);
}

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(produtoRepository.findAll());
    }

    @GetMapping("/{id}")
public ResponseEntity<?> detalhar(@PathVariable Long id) {

    return produtoRepository.findById(id)
            .map(produto -> {

                ProdutoDetalheResponse resposta = new ProdutoDetalheResponse();

                resposta.setId(produto.getId());
                resposta.setNome(produto.getNome());
                resposta.setValorUnitario(produto.getValorUnitario());
                resposta.setQtdeEstoque(produto.getQtdeEstoque());

                Venda ultimaVenda = vendaRepository
                        .findTopByProdutoIdOrderByDataVendaDesc(id)
                        .orElse(null);

                if (ultimaVenda != null) {
                    resposta.setDataUltimaVenda(
                            ultimaVenda.getDataVenda().toString()
                    );

                    resposta.setValorUltimaVenda(
                            ultimaVenda.getValorTotal()
                    );
                }

                return resposta;
            })
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Long id) {
    if (!produtoRepository.existsById(id)) {
        return ResponseEntity.badRequest().body("Produto não encontrado");
    }

    produtoRepository.deleteById(id);
    return ResponseEntity.ok("Produto excluído com sucesso");
}
}