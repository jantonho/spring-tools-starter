package com.carteira;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWorldTest {

	@Test
	public void firstTest() {
		System.out.println("validando configs de testes");
		
		assertEquals(1, 1);
	}
}
