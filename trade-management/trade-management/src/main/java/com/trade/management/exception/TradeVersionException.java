package com.trade.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class TradeVersionException extends Exception {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	   * Instantiates a new Duplicate Trade  found exception.
	   *
	   * @param message the message
	   */
	  public TradeVersionException(String message) {
	    super(message);
	  }
	}