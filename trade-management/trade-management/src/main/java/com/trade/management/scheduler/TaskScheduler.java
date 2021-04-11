package com.trade.management.scheduler;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.trade.management.service.TradeService;

public class TaskScheduler {
	
	private static final Logger log = LoggerFactory.getLogger(TaskScheduler.class);

	@Autowired
	TradeService tradeService;

	@Scheduled(cron = "${trade.expiry.schedule}")
	public void reportCurrentTime() {
		log.info("Time now", LocalDate.now());
		tradeService.updateExpiryFlag();
	}

}
