package com.ford.crudops.students.acceptance.greeting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ford.cloudnative.base.api.BaseBodyResponse;
import com.ford.cloudnative.base.test.acceptance.AcceptanceTestUtil;

import com.ford.crudops.students.greeting.api.GreetingsResponse;

public class GreetingsAcceptanceTest {

	RestTemplate restTemplate;

	@Before
	public void setup() {
		restTemplate = AcceptanceTestUtil.restTemplateBuilder().disableErrorHandler().build();
	}

	@Test
	public void testGetGreetingsEndpoint() throws Exception {
		ResponseEntity<GreetingsResponse> response = restTemplate.getForEntity("/api/v1/greetings", GreetingsResponse.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
