package com.carteira.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.carteira.entity.ItemCarteira;
import com.carteira.entity.TipoItemCarteira;

public interface ItemCarteiraRepository extends JpaRepository<ItemCarteira, Long> {

	Page<ItemCarteira> findByCarteiraIdAndDataGreaterThanEqualAndDataLessThanEqual(Long carteiraSalva, Date dataAtual,
			Date localMais5, Pageable pg);

	List<ItemCarteira> findByCarteiraIdAndTipo(Long itemSalvo, TipoItemCarteira en);

	@Query(value="select sum(valor) from ItemCarteira ic where ic.carteira.id = :idCarteira")
	BigDecimal sumByCarteiraId(Long idCarteira);
}
