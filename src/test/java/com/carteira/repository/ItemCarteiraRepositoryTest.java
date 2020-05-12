package com.carteira.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.carteira.entity.Carteira;
import com.carteira.entity.ItemCarteira;
import com.carteira.entity.TipoItemCarteira;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ItemCarteiraRepositoryTest {

	private static final Date DATA_ATUAL = new Date();
	
	@Autowired
	ItemCarteiraRepository itemCarteiraRepo;
	
	@Autowired
	CarteiraRepository carteiraRepo;
	
	private Long itemSalvo = null;
	private Long carteiraSalva = null;
	
	@Before
	public void antes() {
		Carteira c = new Carteira();
		c.setNome("Carteira 1");
		c.setValor(BigDecimal.valueOf(500));
		carteiraRepo.save(c);
		
		ItemCarteira ic = new ItemCarteira(null, c, DATA_ATUAL, TipoItemCarteira.EN, "Conta de Luz @BEFORE", BigDecimal.valueOf(65));
		itemCarteiraRepo.save(ic);
		
		itemSalvo = ic.getId();
		carteiraSalva = c.getId();
	}
	
	@After
	public void depois() {
		itemCarteiraRepo.deleteAll();
		carteiraRepo.deleteAll();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void salvarItemCarteiraInvalido() {
		ItemCarteira ic = new ItemCarteira(null, null, DATA_ATUAL, null, "Conta de Luz", null);
		itemCarteiraRepo.save(ic);
	}
	
	@Test
	public void atualizar() {
		Optional<ItemCarteira> ic = itemCarteiraRepo.findById(itemSalvo);
		
		String descricao = "Descrição alterada";
		ic.get().setDescricao(descricao);
		
		itemCarteiraRepo.save(ic.get());
		
		Optional<ItemCarteira> newDesIc = itemCarteiraRepo.findById(itemSalvo);
		
		assertEquals(descricao, newDesIc.get().getDescricao());
	}
	
	@Test
	public void remover() {
		Optional<Carteira> c = carteiraRepo.findById(carteiraSalva);
		
		ItemCarteira ics = new ItemCarteira(null, c.get(), DATA_ATUAL, TipoItemCarteira.EN, "Conta de net", BigDecimal.valueOf(65));
		itemCarteiraRepo.save(ics);
		
		itemCarteiraRepo.deleteById(ics.getId());
		
		Optional<ItemCarteira> ic = itemCarteiraRepo.findById(ics.getId());
		
		assertFalse(ic.isPresent());
	}
	
	@Test
	public void listarPorPeriodo() {
		Optional<Carteira> c = carteiraRepo.findById(carteiraSalva);
		
		LocalDateTime ldt = DATA_ATUAL.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		Date localMais5 = DATA_ATUAL.from(ldt.plusDays(5).atZone(ZoneId.systemDefault()).toInstant());
		Date localMais7 = DATA_ATUAL.from(ldt.plusDays(7).atZone(ZoneId.systemDefault()).toInstant());
	
		itemCarteiraRepo.save(new ItemCarteira(null, c.get(), localMais5, TipoItemCarteira.EN, "Conta de Luz", BigDecimal.valueOf(65)));
		itemCarteiraRepo.save(new ItemCarteira(null, c.get(), localMais7, TipoItemCarteira.EN, "Conta de Luz", BigDecimal.valueOf(65)));
		
		PageRequest pg = new PageRequest(0, 5);
		Page<ItemCarteira> response = itemCarteiraRepo.findByCarteiraIdAndDataGreaterThanEqualAndDataLessThanEqual(carteiraSalva, DATA_ATUAL, localMais7, pg);
		
		assertEquals(response.getContent().size(), 3);
		assertEquals(response.getTotalElements(), 3);
		assertEquals(response.getContent().get(0).getCarteira().getId(), carteiraSalva);
	}

	@Test
	public void buscarPorTipo() {
		List<ItemCarteira> ls = itemCarteiraRepo.findByCarteiraIdAndTipo(carteiraSalva, TipoItemCarteira.EN);
		assertEquals(ls.get(0).getTipo(), TipoItemCarteira.EN);
	}
	
	@Test
	public void buscarPorTipoSD() {
		Optional<Carteira> c = carteiraRepo.findById(carteiraSalva);
		itemCarteiraRepo.save(new ItemCarteira(null, c.get(), DATA_ATUAL, TipoItemCarteira.SD, "Conta de Luz", BigDecimal.valueOf(65)));
		
		List<ItemCarteira> ls = itemCarteiraRepo.findByCarteiraIdAndTipo(carteiraSalva, TipoItemCarteira.SD);

		assertEquals(ls.get(0).getTipo(), TipoItemCarteira.SD);
	}
	
	@Test
	public void somaPorCarteira() {
		Optional<Carteira> c = carteiraRepo.findById(carteiraSalva);
		
		itemCarteiraRepo.save(new ItemCarteira(null, c.get(), DATA_ATUAL, TipoItemCarteira.SD, "Conta Combustível", BigDecimal.valueOf(150.80)));
		
		BigDecimal response = itemCarteiraRepo.sumByCarteiraId(carteiraSalva);
		
		assertEquals(response.compareTo(BigDecimal.valueOf(215.80)), 0);
	}
	
}
