package com.coseos.web.server.micro;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * ArgParser is a minimalistic arg parser
 * 
 * @author tlorenz
 *
 */
public class ArgParser {

	private static final String ENDSWITH_SCRIPT_MATCH = ".js";
	private static final String REGEX_HOST_MATCH = "[a-z]+";
	private static final String REGEX_PORT_MATCH = "([0-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])";
	
	private String host = "localhost";
	private int port = 9111;
	private String script = "42";

	public ArgParser(String[] args) {
		for (String arg : args) {
			if (arg.matches(REGEX_PORT_MATCH)) {
				port = Integer.parseUnsignedInt(arg);
			} else if (arg.matches(REGEX_HOST_MATCH)) {
				host = arg;
			} else if (arg.endsWith(ENDSWITH_SCRIPT_MATCH)) {
				try {
					script = String.join("\n", Files.readAllLines((new File(arg)).toPath()));
				} catch (IOException e) {						
					e.printStackTrace();
				}
			}
		}
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getScript() {
		return script;
	}

	public static ArgParser argParser(String[] args) {
		return new ArgParser(args);
	}
}