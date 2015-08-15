package com.pallot.cms.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

@ActiveProfiles({"test","unit"})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { com.pallot.cms.conf.UnitTestApplicationConfig.class })
public class SpringUnitTest {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Test
	public void saveJSON() throws JsonGenerationException, JsonMappingException, IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		ObjectMapper mapper = new ObjectMapper();
		Map<String,String> value = new HashMap<>();
		value.put("name", "value");
		String json = mapper.writeValueAsString(value);
		
		
		DBObject dbObject = (DBObject) JSON.parse(json);
		
		System.out.println(json);
		
		mongoTemplate.insert(dbObject, "test");
		
		assertTrue(mongoTemplate.collectionExists("test"));
		DBCollection collection = mongoTemplate.getCollection("test");
		WriteResult wr = collection.save(dbObject);
		Object id = wr.getUpsertedId();
		
		
		assertEquals(1L, collection.count());
		
		DBObject result = collection.findOne();
		
		Object jsonResult = mapper.readValue(result.toString(), Object.class);
		
		
		Map<String,String> map = (Map<String,String>)jsonResult;
		map.entrySet().stream().forEach(e -> System.out.printf("key [%s] value [%s]\n", e.getKey(), e.getValue()));
		System.out.println(PropertyUtils.getProperty(jsonResult, "name"));
		System.out.println(PropertyUtils.getProperty(jsonResult, "_id.$oid"));
		
		System.out.println(result);
		System.out.println(id);
		System.out.println(jsonResult);
	}

	protected void importJSON(String collection, String data) {
		mongoTemplate.save(data, collection);
	}
}