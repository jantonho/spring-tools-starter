package com.carteira.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.carteira.entity.Carteira;
import com.carteira.entity.ItemCarteira;
import com.carteira.entity.TipoItemCarteira;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemCarteiraRepositoryTest {

	@Autowired
	ItemCarteiraRepository itemCarteiraRepo;
	
	@Autowired
	CarteiraRepository carteiraRepo;
	
	@Test
	public void salvar() {
		Carteira c = new Carteira();
		c.setNome("Carteira 1");
		c.setValor(BigDecimal.valueOf(500));
		carteiraRepo.save(c);
		
		ItemCarteira ic = new ItemCarteira(1L, c, new Date(), TipoItemCarteira.EN, "Conta de Luz", BigDecimal.valueOf(65));

		ItemCarteira icr = itemCarteiraRepo.save(ic);
		
		assertNotNull(icr);
		assertEquals(icr.getDescricao(), "Conta de Luz");
		assertEquals(icr.getTipo(), TipoItemCarteira.EN);

	}
}
