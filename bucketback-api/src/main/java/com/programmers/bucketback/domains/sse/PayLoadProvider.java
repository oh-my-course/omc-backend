package com.programmers.bucketback.domains.sse;

import java.util.Map;

public interface PayLoadProvider {
	Map<String, Object> getPayloadData();
}
