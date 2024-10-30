package com.challenge.distribution.centers;

import org.springframework.boot.SpringApplication;

public class TestChallengeDistributionCentersApplication {

	public static void main(String[] args) {
		SpringApplication.from(ChallengeDistributionCentersApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
