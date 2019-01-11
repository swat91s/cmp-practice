package com.ford.crudops.students.greeting;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ford.cloudnative.base.app.web.exception.handler.ExceptionHandlerConfiguration;
import com.ford.cloudnative.base.test.response.BaseBodyErrorResultMatchers;

import com.ford.crudops.students.greeting.api.CreateGreetingRequest;
import com.ford.crudops.students.greeting.Greeting;
import com.ford.crudops.students.greeting.GreetingController;
import com.ford.crudops.students.greeting.GreetingMapper;
import com.ford.crudops.students.greeting.GreetingService;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {GreetingController.class, GreetingMapper.class}, secure = false)
@Import(ExceptionHandlerConfiguration.class)
public class GreetingControllerIntegrationTest {

	private static final OffsetDateTime SAMPLE_OFFSET_DATE_TIME = OffsetDateTime.parse("2018-02-28T13:34:42.921-05:00");

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	RequestMappingHandlerAdapter adapter;

	@MockBean
	GreetingService greetingService;

	@SpyBean
	GreetingMapper greetingMapper;

	List<Greeting> greetings;

	@Before
	public void setup() {
		this.greetings = Arrays.asList(
				Greeting.builder().id(1L).message("MESSAGE1").authorName("AUTHORNAME1").created(SAMPLE_OFFSET_DATE_TIME).build(),
				Greeting.builder().id(2L).message("MESSAGE2").authorName("AUTHORNAME2").created(SAMPLE_OFFSET_DATE_TIME).build()
				);

		when(this.greetingService.getAllGreetings()).thenReturn(this.greetings);
		when(this.greetingService.getGreeting(1L)).thenReturn(Optional.of(this.greetings.get(0)));
		when(this.greetingService.getGreeting(2L)).thenReturn(Optional.of(this.greetings.get(1)));
		when(this.greetingService.getGreeting(999L)).thenReturn(Optional.empty());
	}

	/***********************************************************************************************
	 * ENDPOINT: GET /api/v1/greetings
	 ***********************************************************************************************/

	@Test
	public void should_return200ReponseWithFieldsPopulated_forGreetingsEndpoint() throws Exception {
		jsonGet("/api/v1/greetings")
			.andExpect(status().isOk())
			// controller unit tests test completeness of response object; we only smoke test here that a JSON response is returned
			.andExpect(jsonPath("$.result.greetings[0].id").value(this.greetings.get(0).getId()))
			.andExpect(jsonPath("$.result.greetings[0].message").value(this.greetings.get(0).getMessage()))
			.andExpect(jsonPath("$.result.greetings[0].authorName").value(this.greetings.get(0).getAuthorName()))
			.andExpect(jsonPath("$.result.greetings[0].created").value(this.greetings.get(0).getCreated().toString()))
			.andExpect(jsonPath("$.result.greetings[1].id").value(this.greetings.get(1).getId()))
			.andExpect(jsonPath("$.result.greetings[1].message").value(this.greetings.get(1).getMessage()))
			.andExpect(jsonPath("$.result.greetings[1].authorName").value(this.greetings.get(1).getAuthorName()))
			.andExpect(jsonPath("$.result.greetings[1].created").value(this.greetings.get(1).getCreated().toString()))
			;
	}

	/***********************************************************************************************
	 * ENDPOINT: GET /api/v1/greetings/{id}
	 ***********************************************************************************************/

	@Test
	public void should_return200ReponseWithFieldsPopulated_forExistingGreeting() throws Exception {
		jsonGet("/api/v1/greetings/2")
			.andExpect(status().isOk())
			// controller unit tests test completeness of response object; we only smoke test here that a JSON response is returned
			.andExpect(jsonPath("$.result.greeting.id").value(this.greetings.get(1).getId()))
			.andExpect(jsonPath("$.result.greeting.message").value(this.greetings.get(1).getMessage()))
			.andExpect(jsonPath("$.result.greeting.authorName").value(this.greetings.get(1).getAuthorName()))
			.andExpect(jsonPath("$.result.greeting.created").value(this.greetings.get(1).getCreated().toString()))
			;
	}

	@Test
	public void should_returnNotFoundReponseWithFieldsPopulated_forNonExistentGreeting() throws Exception {
		jsonGet("/api/v1/greetings/999")
			.andExpect(status().isNotFound())
			;
	}

	/***********************************************************************************************
	 * ENDPOINT: POST /api/v1/greetings
	 ***********************************************************************************************/

	@Test
	public void should_return200ReponseWithFieldsPopulated_forCreateGreetingEndpoint() throws Exception {
		CreateGreetingRequest request = CreateGreetingRequest.builder().message("MESSAGE").authorName("AUTHORNAME").build();
		Greeting greetingCreated = Greeting.builder().id(5L).message("MESSAGE").authorName("AUTHORNAME").created(SAMPLE_OFFSET_DATE_TIME).build();
		when(this.greetingService.create(this.greetingMapper.toGreeting(request))).thenReturn(greetingCreated);
		
		jsonPost("/api/v1/greetings", request)
			.andExpect(status().isCreated())
			// controller unit tests test completeness of response object; we only smoke test here that a JSON response is returned
			.andExpect(jsonPath("$.result.greeting.id").value(greetingCreated.getId()))
			.andExpect(jsonPath("$.result.greeting.message").value(greetingCreated.getMessage()))
			.andExpect(jsonPath("$.result.greeting.authorName").value(greetingCreated.getAuthorName()))
			.andExpect(jsonPath("$.result.greeting.created").value(greetingCreated.getCreated().toString()))
			;
	}
	
	@Test
	public void should_validateMessageField_forCreateGreetingsEndpoint() throws Exception {
		CreateGreetingRequest request = CreateGreetingRequest.builder().build();
		
		jsonPost("/api/v1/greetings", request)
			.andExpect(status().isBadRequest())
			.andExpect(BaseBodyErrorResultMatchers.dataErrorWithCode("message", "NotNull").exists())
			;
	}

	/////////////////////// Helper Methods

	ResultActions jsonGet(String url) throws Exception {
		return this.mockMvc.perform(MockMvcRequestBuilders.get(url))
				.andDo(MockMvcResultHandlers.print());
	}

	ResultActions jsonPost(String url, Object entity) throws Exception {
		return 
			this.mockMvc.perform(MockMvcRequestBuilders
				.post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(entity))
			)
			.andDo(MockMvcResultHandlers.print());
	}

}
