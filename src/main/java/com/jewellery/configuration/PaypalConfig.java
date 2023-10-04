package com.jewellery.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;



@Configuration
public class PaypalConfig {
	@Value("${paypal.client.id}")
	private String clientId;
	@Value("${paypal.client.secret}")
	private String clientSecret;
	@Value("${paypal.mode}")
	private String clientMode;
	
	
	@Bean
	public Map<String,String> paypalSdkConfig(){
		Map<String,String> configmap= new HashMap<>();
		configmap.put("mode",clientMode);
		return configmap;
	}
	@Bean
	public OAuthTokenCredential oAuthTokenCredential() {
		return new OAuthTokenCredential(clientId,clientSecret,paypalSdkConfig());
	}
	@Bean
	public APIContext apiContext() throws PayPalRESTException {
		APIContext api = new APIContext(oAuthTokenCredential().getAccessToken());
		api.setConfigurationMap(paypalSdkConfig());
		return api;
	}
	

}
