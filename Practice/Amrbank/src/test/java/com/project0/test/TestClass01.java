package com.project0.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestClass01 {
	
	
	@BeforeAll
	public static void beforeAll() {
		System.out.println("helllo from before all");
		
	}
	@Test
	public void test01() {
		System.out.println("Hello from test01");
	}
	
	@Test
	public void test02() {
		System.out.println("Hello from test02");
	}
	
	
	@AfterAll
	public static void afterAll() {
		System.out.println("Hello from After All");
	}
	
	

}
