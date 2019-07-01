package com.golinko.statement.processor;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("apitest")
@Configuration
@ComponentScan(basePackages = {"com.golinko.statement.processor"})
@EnableAutoConfiguration
public class ApplicationTestConfig {}
