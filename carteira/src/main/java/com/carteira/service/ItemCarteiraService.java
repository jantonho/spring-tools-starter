package com.carteira.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.carteira.entity.ItemCarteira;
import com.carteira.entity.TipoItemCarteira;

public interface ItemCarteiraService {

	ItemCarteira salvar(ItemCarteira itemCarteira);

	Page<ItemCarteira> buscarPorPeriodo(Long idCarteira, Date dataInicio, Date dataFim, int pagina);

	List<ItemCarteira> buscarPorCarteiraETipo(Long idCarteira, TipoItemCarteira tipo);

	BigDecimal SomarPorCarteira(Long idCarteira);
	
	Optional<ItemCarteira> buscarPorId(Long id);
	
	void removerPorId(Long id);
}
