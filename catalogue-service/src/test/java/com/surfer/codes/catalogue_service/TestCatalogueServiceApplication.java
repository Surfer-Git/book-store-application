package com.surfer.codes.catalogue_service;

import org.springframework.boot.SpringApplication;

public class TestCatalogueServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(CatalogueServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
