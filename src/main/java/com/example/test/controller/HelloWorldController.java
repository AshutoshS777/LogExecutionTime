/**
 * 
 */
package com.example.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.utils.LogExecutionTime;

/**
 * @author Ashutosh
 *
 */
@RestController
public class HelloWorldController {
	
	@LogExecutionTime
	@GetMapping("/greeting")
	public String greeting() {
		return "Hello World";
	}

}
