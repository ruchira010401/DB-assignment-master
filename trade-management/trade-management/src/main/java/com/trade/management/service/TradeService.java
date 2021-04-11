package com.trade.management.service;

import java.util.List;

import javax.validation.Valid;

import com.trade.management.exception.MaturityDateException;
import com.trade.management.exception.ResourceNotFoundException;
import com.trade.management.exception.TradeVersionException;
import com.trade.management.model.Trade;

public interface TradeService {
	


	List<Trade> getTradeList();
	
	Trade addTrade(Trade trade) throws  TradeVersionException, MaturityDateException;

	Trade updateTrade(@Valid Trade trade) throws ResourceNotFoundException, TradeVersionException, MaturityDateException;

	void updateExpiryFlag();

}
