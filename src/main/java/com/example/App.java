package com.example;

import java.io.IOException;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App 
{
	
	private void loadProperties() throws IOException {
		final Properties properties = new Properties();
    	properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
    	System.out.println(properties.getProperty("version"));
	}
	
    public static void main( String[] args ) throws IOException
    {
    	new App().loadProperties();
    }
}
