/**
 * 
 */
package com.trade.management.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trade.management.exception.MaturityDateException;
import com.trade.management.exception.ResourceNotFoundException;
import com.trade.management.exception.TradeVersionException;
import com.trade.management.model.Trade;
import com.trade.management.repository.TradeRepository;
import com.trade.management.service.TradeService;

/**
 * @author Ruchira
 *
 */
@Service
public class TradeServiceImpl implements TradeService {

	private static final Logger logger = LoggerFactory.getLogger(TradeServiceImpl.class);

	@Autowired
	TradeRepository tradeRepository;

	@Override
	public List<Trade> getTradeList() {
		return tradeRepository.findAll();
	}

	@Override
	public Trade updateTrade(Trade trade)
			throws ResourceNotFoundException, TradeVersionException, MaturityDateException {
		Trade updatedTrade = new Trade();
		Trade responsetrade = checkIfTradeExist(trade.getTradeId());
		boolean validTrade = validateTrade(responsetrade, trade);
		if (validTrade) {
			updatedTrade = tradeRepository.save(trade);
		}

		return updatedTrade;
	}

	@Override
	public Trade addTrade(Trade trade) throws TradeVersionException, MaturityDateException {
		Trade updatedTrade = new Trade();
		Trade responsetrade = new Trade();
		boolean exist = false;
		try {
			responsetrade = checkIfTradeExist(trade.getTradeId());
			exist = true;
			boolean validTrade = validateTrade(responsetrade, trade);
			if (validTrade) {
				updatedTrade = tradeRepository.save(trade);
			}
		} catch (ResourceNotFoundException e) {
			exist = false;
			logger.info("No Existing Record found");
		}
		
		if(!exist) {
			boolean dateCriteria = checkDateCriteria(trade.getMaturityDate());
			if (!dateCriteria) {
				throw new MaturityDateException("Maturity Date Criteria Not Staisfied");
			}
			updatedTrade = tradeRepository.save(trade);
		}
		
		return updatedTrade;
	}

	private boolean validateTrade(Trade responseTrade, Trade trade)
			throws TradeVersionException, MaturityDateException {
		boolean versionCriteria = checkVersionCriteria(responseTrade.getVersion(), trade.getVersion());
		if (versionCriteria == true) {
			logger.info("Version Criteria Satisfied");
			boolean dateCriteria = checkDateCriteria(trade.getMaturityDate());
			if (dateCriteria == true) {
				logger.info("Maturity Date Criteria Satisfied");
				return true;
			} else {
				// Maturity Date Criteria not satisfied
				throw new MaturityDateException("Maturity Date Criteria Not Staisfied");
			}
		} else {
			// "Version Number is greater than the existing version"
			logger.info("Version Number is greater than the existing version");
			return false;
		}
	}

	private boolean checkDateCriteria(LocalDate maturityDate) {
		if (maturityDate.isAfter(LocalDate.now())) {
			return true;
		}
		return false;
	}

	private boolean checkVersionCriteria(int responseVersion, int inputVersion) throws TradeVersionException {
		if (responseVersion == inputVersion) {
			return true;
		} else if (responseVersion > inputVersion) {
			throw new TradeVersionException("Version should be greater than the existing one");
		}

		return false;

	}

	private Trade checkIfTradeExist(String tradeID) throws ResourceNotFoundException {
		return tradeRepository.findById(tradeID)
				.orElseThrow(() -> new ResourceNotFoundException("TradeID " + tradeID + " not found"));
	}

	@Override
	public void updateExpiryFlag() {
		 tradeRepository.findAll().stream().forEach(t -> {
			if( t.getMaturityDate().isBefore(LocalDate.now()) ){
				 t.setIsExpired("Y");
				 tradeRepository.save(t);
			}
         });
     }

}
