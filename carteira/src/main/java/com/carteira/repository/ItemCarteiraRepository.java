package com.carteira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carteira.entity.ItemCarteira;

public interface ItemCarteiraRepository extends JpaRepository<ItemCarteira, Long> {

}
