package com.trade.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class MaturityDateException 
	extends Exception {

		  /**
		   * Instantiates a new Duplicate Trade  found exception.
		   *
		   * @param message the message
		   */
		  public MaturityDateException(String message) {
		    super(message);
		  }
}
