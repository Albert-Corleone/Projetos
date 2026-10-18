package br.com.projeto.api_vendas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projeto.api_vendas.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    Optional<Venda> findTopByProdutoIdOrderByDataVendaDesc(Long produtoId);
}