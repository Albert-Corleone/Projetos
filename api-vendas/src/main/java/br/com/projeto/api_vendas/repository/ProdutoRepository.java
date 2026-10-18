package br.com.projeto.api_vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projeto.api_vendas.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}