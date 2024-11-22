package com.example.ShopSphere.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public final class Messages {

	@Autowired
	private MessageSource source;

	
	public String get(String code) {
		return source.getMessage(code, null,Locale.ENGLISH);
	}
}
