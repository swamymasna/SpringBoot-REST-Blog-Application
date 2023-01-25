package com.swamy.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Endpoint(id = "features")
public class FeatureEndpoint {

	private final Map<String, Feature> featuresMap = new ConcurrentHashMap<>();

	public FeatureEndpoint() {

		featuresMap.put("Post", new Feature(true));
		featuresMap.put("Comment", new Feature(true));
		featuresMap.put("User", new Feature(false));
	}

	@ReadOperation
	public Map<String, Feature> features() {
		return featuresMap;
	}

	@ReadOperation
	public Feature feature(@Selector String featureName) {
		return featuresMap.get(featureName);
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Feature {
		private boolean isEnabled;
	}
}
