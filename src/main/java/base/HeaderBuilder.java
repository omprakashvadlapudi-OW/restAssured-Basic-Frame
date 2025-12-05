package base;

import java.util.HashMap;
import java.util.Map;

public class HeaderBuilder {

	public static Map<String, Object> defaultHeaders() {
		Map<String, Object> map = new HashMap<>();
		map.put("Content-Type", "application/json");
		return map;
	}

	public static Map<String, Object> tokenHeaders(String token, String tenantId) {
		Map<String, Object> map = defaultHeaders();
		map.put("Authorization", "Bearer " + token);
		map.put("TenantId", tenantId);
		return map;
	}

	public static Map<String, Object> tokenHeaders(String token, String tenantId, String accept) {
		Map<String, Object> map = defaultHeaders();
		map.put("Authorization", "Bearer " + token);
		map.put("TenantId", tenantId);
		map.put("Accept", accept);
		return map;
	}

}
