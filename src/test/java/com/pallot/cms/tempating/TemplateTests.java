package com.pallot.cms.tempating;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class TemplateTests {
	@Test
	public void test() throws JsonParseException, JsonMappingException, IOException {
		//Given
		final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCharacterEncoding("UTF-8");
		final TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);

		Context context = new Context();
		context.setVariable("name", "marcus");

		Map<String, String> map = new HashMap<>();
		map.put("name", "steve");
		context.setVariable("map", map);
		
		String json = "{ \"_id\" : { \"$oid\" : \"55cf1eb496d95c654ffbe4d6\"} , \"name\" : \"ian\"}";
		ObjectMapper mapper = new ObjectMapper();
		Object jsonObj = mapper.readValue(json, Object.class);
		context.setVariable("jsonObj", jsonObj);

		json = "{ \"detail\" : {\"shortDescription\": \"Great Pen\"}}";
		jsonObj = mapper.readValue(json, Object.class);
		context.setVariable("product", jsonObj);
		
		//When
		final String result = templateEngine.process("templates/test.html", context);

		//Then
		System.out.println(result);
		assertTrue(result.contains("marcus"));
		assertTrue(result.contains("steve"));
		assertTrue(result.contains("ian"));
		assertTrue(result.contains("Great Pen"));
		
		

	}
}
