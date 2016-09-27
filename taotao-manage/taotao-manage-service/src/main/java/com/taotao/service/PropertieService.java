package com.taotao.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertieService {
	@Value("${REPOSITORY_PATH}")
	public static String REPOSITORY_PATH;

	@Value("${IMAGE_BASE_URL}")
	public static String IMAGE_BASE_URL;
}
