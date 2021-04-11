package com.trade.management.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trade.management.exception.MaturityDateException;
import com.trade.management.exception.ResourceNotFoundException;
import com.trade.management.exception.TradeVersionException;
import com.trade.management.model.Trade;
import com.trade.management.service.TradeService;

@RestController
@RequestMapping("/api/trades")
public class TradeController {
	
	private static final Logger logger = LoggerFactory.getLogger(TradeController.class);

	@Autowired
	TradeService tradeService;

	@GetMapping("/tradeList")
	public ResponseEntity<?> getTradeList() {
		logger.info("Get Trade List");
		List<Trade> tradeList =  tradeService.getTradeList();
		return new ResponseEntity<>(tradeList,null,HttpStatus.OK);
	}

	@PostMapping("/updateTrade") 
	public ResponseEntity<?> updateTrade(@Valid @RequestBody Trade trade) {
		logger.info("Update Trade");
		try {
			Trade responseTrade = tradeService.updateTrade(trade);
			return new ResponseEntity<>(responseTrade,null,HttpStatus.OK);
		} catch (TradeVersionException e) {
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.PRECONDITION_FAILED);
		} catch (MaturityDateException e) {
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.PRECONDITION_FAILED);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@PostMapping("/addTrade") 
	public ResponseEntity<?> addTrade(@Valid @RequestBody Trade trade) {
		try {
			Trade responseTrade = tradeService.addTrade(trade);
			return new ResponseEntity<>(responseTrade,null,HttpStatus.OK);
		} catch (TradeVersionException e) {
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.PRECONDITION_FAILED);
		} catch (MaturityDateException e) {
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.PRECONDITION_FAILED);
		}
		
	}

}
