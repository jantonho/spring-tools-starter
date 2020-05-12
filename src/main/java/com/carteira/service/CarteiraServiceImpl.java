package com.carteira.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carteira.entity.Carteira;
import com.carteira.repository.CarteiraRepository;

@Service
public class CarteiraServiceImpl implements CarteiraService {

	@Autowired
	CarteiraRepository carteiraRep;

	@Override
	public Carteira save(Carteira u) {
		return carteiraRep.save(u);
	}
}
