package com.carteira.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.carteira.entity.Carteira;
import com.carteira.entity.ItemCarteira;
import com.carteira.entity.TipoItemCarteira;
import com.carteira.repository.ItemCarteiraRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ItemCarteiraServiceTest {

	private static final Date DATA_ATUAL = new Date();
	private static final String DESCRICAO = "Conta de Celular";
	private static final BigDecimal VALOR = BigDecimal.valueOf(65);

	@MockBean
	ItemCarteiraRepository itemCarteiraRep;

	@Autowired
	ItemCarteiraService itemCarteiraService;

	@Test
	public void salvar() {
		BDDMockito.given(itemCarteiraRep.save(Mockito.any(ItemCarteira.class))).willReturn(getMockItemCarteira());

		ItemCarteira ic = itemCarteiraService.salvar(new ItemCarteira());

		assertNotNull(ic);
		assertEquals(ic.getDescricao(), DESCRICAO);
		assertEquals(ic.getValor().compareTo(VALOR), 0);
	}

	@Test
	public void listaPorPeriodo() {
		List<ItemCarteira> ls = new ArrayList<>();
		ls.add(getMockItemCarteira());

		Page<ItemCarteira> page = new PageImpl<>(ls);
		BDDMockito
				.given(itemCarteiraRep.findByCarteiraIdAndDataGreaterThanEqualAndDataLessThanEqual(Mockito.anyLong(),
						Mockito.any(Date.class), Mockito.any(Date.class), Mockito.any(PageRequest.class)))
				.willReturn(page);

		Page<ItemCarteira> response = itemCarteiraService.buscarPorPeriodo(1L, DATA_ATUAL, DATA_ATUAL, 0);

		assertNotNull(response);
		assertEquals(response.getContent().size(), 1);
		assertEquals(response.getContent().get(0).getDescricao(), DESCRICAO);
	}

	private ItemCarteira getMockItemCarteira() {
		Carteira c = new Carteira();
		c.setId(1L);

		ItemCarteira ic = new ItemCarteira(1L, c, DATA_ATUAL, TipoItemCarteira.SD, DESCRICAO, VALOR);

		return ic;
	}

}
