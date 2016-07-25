/**
 * api v1 http basic auth认证过滤器
 */
package com.yamixed.api.v1.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.codec.binary.Base64;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

/**
 * @author xiaxue
 *
 */
public class BasicAuthFilter implements ContainerRequestFilter{
	
	private static Properties prop =  new  Properties();
	
	static{
		InputStream inputStream = BasicAuthFilter.class.getResourceAsStream("/system.properties");
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public ContainerRequest filter(ContainerRequest request) {
		String auth = request.getHeaderValue("authorization");
		if(auth == null){
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }
		String[] lap = decode(auth);
        if(lap == null || lap.length != 2){
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }
        if(prop.getProperty("basicAuth.user").equals(lap[0]) && prop.getProperty("basicAuth.password").equals(lap[1])){
        	return request;
        }
        throw new WebApplicationException(Status.UNAUTHORIZED);
	}
	
	
	private String[] decode(String auth){
		try {
			auth = auth.replaceFirst("[B|b]asic ", "");
			byte[] decodedBytes = Base64.decodeBase64(auth);
			String usernameAndPassword = new String(decodedBytes, "UTF-8");
			final StringTokenizer tokenizer = new StringTokenizer(
					usernameAndPassword, ":");
			final String username = tokenizer.nextToken();
			final String password = tokenizer.nextToken();
			return new String[]{username,password};
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
