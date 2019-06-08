package com.coseos.web.server.micro;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import io.undertow.Undertow;
import io.undertow.server.HttpServerExchange;

/**
 * WebServer is a minimalistic web server
 * 
 * @author tlorenz
 *
 */
public class WebServer {

	static Logger logger = Logger.getLogger("com.coseos.web.server.micro");

	private static ArgParser argParser;
	private static ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
	private static ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("javascript");

	public static void main(String[] args) throws IOException {
		new WebServer().run(args);
	}
	
	public void run(String[] args) {
		argParser = ArgParser.argParser(args);
		logger.log(Level.INFO,
			() -> String.format("Listening on %s %d", argParser.getHost(), argParser.getPort()));

		Undertow.builder()
		    .addHttpListener(argParser.getPort(), argParser.getHost())
		    .setHandler(WebServer::handle).build()
			.start();
	}

	public static void handle(HttpServerExchange exchange) {
		logger.log(Level.INFO, exchange.getRequestURL());

		try {
			scriptEngine.put("exchange", exchange);
			scriptEngine.eval(argParser.getScript());
			return;
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		exchange.setStatusCode(500);
		exchange.getResponseSender()
			.send(String.join("\n",
				"<!doctype html>",
				"<html lang='en'>",
				"<head>",
				"  <title>endpoint</title>",
				"  <meta charset='utf-8'>",
				"</head>", "<body>",
				"  <h1>Internal Server Error</h1>",
				"  <p>Please check the server logs for details</p>",
				"</body>",
				"</html>"));
	}
}
