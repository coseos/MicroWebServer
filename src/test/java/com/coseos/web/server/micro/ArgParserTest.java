package com.coseos.web.server.micro;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ArgParserTest {
    private static final String HOST_REMOTEHOST = "remotehost";
    private static final String HOST_LOCALHOST = "localhost";
    private static final String PORT_8080 = "8080";
    private static final String SCRIPT_FILE_CONTENT = "return;";
    
	public static String[] hostArgs = new String[] { HOST_REMOTEHOST };
	public static String[] portArgs = new String[] { PORT_8080 };
	
	@Test
	public void testHost() {
		// act
		ArgParser argParser = ArgParser.argParser(hostArgs);
		
		// assert
		assertThat(argParser,is(notNullValue()));
		assertThat(argParser.getHost(),is(equalTo(HOST_REMOTEHOST)));
		assertThat(argParser.getPort(),is(9111));
		assertThat(argParser.getScript(),is(equalTo("42")));
	}
	
	@Test
	public void testPort() {
		// act
		ArgParser argParser = ArgParser.argParser(portArgs);
		
		// assert
		assertThat(argParser,is(notNullValue()));
		assertThat(argParser.getHost(),is(equalTo(HOST_LOCALHOST)));
		assertThat(argParser.getPort(),is(8080));
		assertThat(argParser.getScript(),is(equalTo("42")));
	}
	
	@Test
	public void testScript(@TempDir Path tempDir) throws IOException {
		Path scriptPath = tempDir.resolve("script.js").toAbsolutePath();
		String scriptFile = scriptPath.toString();
		Files.write(scriptPath, SCRIPT_FILE_CONTENT.getBytes());
		
		String[] scriptArgs = new String[] { scriptFile };
		
		// act
		ArgParser argParser = ArgParser.argParser(scriptArgs);
		
		// assert
		assertThat(argParser,is(notNullValue()));
		assertThat(argParser.getHost(),is(equalTo(HOST_LOCALHOST)));
		assertThat(argParser.getPort(),is(9111));
		assertThat(argParser.getScript(),is(equalTo(SCRIPT_FILE_CONTENT)));
	}
}
