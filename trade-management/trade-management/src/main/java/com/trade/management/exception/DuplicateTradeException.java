package com.trade.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Duplicate Trade found exception.
 *
 * @author Ruchira Sawant 
 */
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class DuplicateTradeException extends Exception {

	  /**
	   * Instantiates a new Duplicate Trade  found exception.
	   *
	   * @param message the message
	   */
	  public DuplicateTradeException(String message) {
	    super(message);
	  }
	}

