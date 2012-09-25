package com.openshift.notebook.core.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@ImportResource("classpath:applicationContext-mongo.xml")
public class MongoDbConfig {

	@Inject
	private MongoDbFactoryConfig mongoDbFactoryConfig;

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactoryConfig.mongoDbFactory());
		return mongoTemplate;
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public ValidatingMongoEventListener validatingMongoEventListener() {
		ValidatingMongoEventListener validatingMongoEventListener = new ValidatingMongoEventListener(validator());
		return validatingMongoEventListener;
	}
}
