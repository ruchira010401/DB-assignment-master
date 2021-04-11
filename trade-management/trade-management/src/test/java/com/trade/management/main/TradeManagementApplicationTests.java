package com.trade.management.main;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.trade.management.TradeManagementApplication;
import com.trade.management.model.Trade;
import com.trade.management.service.impl.TradeServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TradeManagementApplicationTests {

	private static final Logger logger = LoggerFactory.getLogger(TradeManagementApplicationTests.class);

	@Autowired
	private TestRestTemplate restTemplate;

	private String getRootUrl() {
		return "http://localhost:8090" + "/api/trades";
	}

	@Test
	public void contextLoads() {
	}

	/**
	 * Here we test that we can get all the trades in the database using the GET
	 * method
	 */
	@Test
	public void getAllTrades() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<?> response = restTemplate.exchange(getRootUrl() + "/tradeList", HttpMethod.GET, entity,
				String.class);

		Assert.assertNotNull(response.getBody());
	}

	/**
	 * Here we test addition of new Trade
	 */

	@Test
	public void testAddTradeSuccess() {
		Trade trade = getTradeDetails();
		ResponseEntity<Trade> postResponse = restTemplate.postForEntity(getRootUrl() + "/addTrade", trade, Trade.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());

	}

	/**
	 * Here we test version failure test
	 */
	@Test
	public void testVersionFailed() {
		Trade trade = getTradeDetailsVF();
		ResponseEntity<String> postResponse = restTemplate.postForEntity(getRootUrl() + "/addTrade", trade, String.class);
		
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
		Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, postResponse.getStatusCode());
	}
	
	/**
	 * Here we test update version success test
	 */
	
	@Test
	public void testVersionSuccess() {
		Trade trade = getTradeDetailsVS();
		ResponseEntity<?> postResponse = restTemplate.postForEntity(getRootUrl() + "/addTrade", trade, Trade.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
		Assert.assertEquals(HttpStatus.OK, postResponse.getStatusCode());
	}
	
	/**
	 * Here we test update maturity date failed test
	 */
	
	@Test
	public void testMaturityDateFailure() {
		Trade trade = getTradeDetailsMDF();
		ResponseEntity<String> postResponse = restTemplate.postForEntity(getRootUrl() + "/addTrade", trade, String.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
		Assert.assertSame(HttpStatus.PRECONDITION_FAILED, postResponse.getStatusCode());
	}

	private Trade getTradeDetails() {
		Trade trade = new Trade();
		trade.setTradeId("T3");
		trade.setVersion(3);
		trade.setBookId("B1");
		trade.setCounterPartyId("CP-2");
		trade.setMaturityDate(LocalDate.of(2021, 8, 25));
		trade.setCreatedDate(LocalDate.now());
		trade.setIsExpired("N");
		return trade;
	}

	private Trade getTradeDetailsVF() {
		Trade trade = new Trade();
		trade.setTradeId("T3");
		trade.setVersion(2);
		trade.setBookId("B1");
		trade.setCounterPartyId("CP-2");
		trade.setMaturityDate(LocalDate.of(2021, 8, 25));
		trade.setCreatedDate(LocalDate.now());
		trade.setIsExpired("N");
		return trade;
	}
	
	private Trade getTradeDetailsVS() {
		Trade trade = new Trade();
		trade.setTradeId("T3");
		trade.setVersion(3);
		trade.setBookId("B1");
		trade.setCounterPartyId("CP-1");
		trade.setMaturityDate(LocalDate.of(2021, 8, 25));
		trade.setCreatedDate(LocalDate.now());
		trade.setIsExpired("N");
		return trade;
	}
	
	private Trade getTradeDetailsMDF() {
		Trade trade = new Trade();
		trade.setTradeId("T3");
		trade.setVersion(3);
		trade.setBookId("B1");
		trade.setCounterPartyId("CP-1");
		trade.setMaturityDate(LocalDate.of(2020, 8, 25));
		trade.setCreatedDate(LocalDate.now());
		trade.setIsExpired("N");
		return trade;
	}

}
