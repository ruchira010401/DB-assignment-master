package com.trade.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trade.management.model.Trade;

public interface TradeRepository extends JpaRepository<Trade, String>{

}
