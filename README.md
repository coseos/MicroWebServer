# MicroWebServer
A simple web server in java for use in development

## Usage

Build it. Run it. Done.

## Configuration

There are three configurable parameters for MicroWebServer

### Host

To change the host, provide the hostname as a parameter.

For now, it has to be a host name. IP's are not recognized.

The default hostname is *localhost*

### Port

To change the port, provide the port number as a parameter.

The default port number is *9111*

### Script

To change the script, provide a script filename as a parameter

The script filename has to end with *.js*

The default script does nothing.

## Scripting

Use Javascript to serve some content. The Nashorn engine, provided
by Java is used to execute the script. Since the MicroWebServer is
based on Undertow, you have to use Undertows HttpServerExchange to
provide the content. This is actually very easy and powerful.

### Example script

```
var Headers = Java.type("io.undertow.util.Headers");

exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html; charset=utf-8");

var html = (function() {/*<!doctype html>
<html>
  <head>
    <title>MicroWebServer</title>
    <meta charset="utf-8">
  </head>
  <body>
    <h1>To serve and test</h1>
    <p></p>
  </body>
</html>
*/}).toString().match(/[^]*\/\*([^]*)\*\/\}$/)[1];

exchange.getResponseSender().send(html);
```

## Authors

* **Thorsten J. Lorenz**

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

## Web

[coseos.com](https://coseos.com/)