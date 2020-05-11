package com.carteira.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.carteira.entity.ItemCarteira;
import com.carteira.entity.TipoItemCarteira;
import com.carteira.repository.ItemCarteiraRepository;

@Service
public class ItemCarteiraServiceImpl implements ItemCarteiraService {

	@Autowired
	private ItemCarteiraRepository repository;

	@Value("${paginacao.itens_por_pagina}")
	private int itensPorPagina;

	@Override
	@CacheEvict(value = "buscarPorCarteiraETipo", allEntries = true)
	public ItemCarteira salvar(ItemCarteira itemCarteira) {
		return repository.save(itemCarteira);
	}

	@Override
	public Page<ItemCarteira> buscarPorPeriodo(Long idCarteira, Date dataInicio, Date dataFim, int pagina) {
		
		@SuppressWarnings("deprecation")
		PageRequest pg = new PageRequest(pagina, itensPorPagina);
		
		return repository.findByCarteiraIdAndDataGreaterThanEqualAndDataLessThanEqual(idCarteira, dataInicio, dataFim,
				pg);
	}

	@Override
	@Cacheable(value = "buscarPorCarteiraETipo")
	public List<ItemCarteira> buscarPorCarteiraETipo(Long idCarteira, TipoItemCarteira tipo) {
		return repository.findByCarteiraIdAndTipo(idCarteira, tipo);
	}

	@Override
	public BigDecimal SomarPorCarteira(Long idCarteira) {
		return repository.sumByCarteiraId(idCarteira);
	}

	@Override
	public Optional<ItemCarteira> buscarPorId(Long id) {
		return repository.findById(id);
	}

	@Override
	@CacheEvict(value = "buscarPorCarteiraETipo", allEntries = true)
	public void removerPorId(Long id) {
		repository.deleteById(id);
	}

}
