package com.carteira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carteira.entity.Carteira;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {

}
