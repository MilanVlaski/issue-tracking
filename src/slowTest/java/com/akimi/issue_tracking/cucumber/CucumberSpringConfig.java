package com.akimi.issue_tracking.cucumber;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import io.cucumber.spring.CucumberContextConfiguration;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberSpringConfig {
}
