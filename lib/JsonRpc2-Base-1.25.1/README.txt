JSON-RPC 2.0 Base

Copyright (c) Vladimir Dzhuvinov, 2009 - 2012


README

This package provides Java classes to represent, parse and serialise JSON-RPC
2.0 requests, notifications and responses.



Requirements:

	* Java 1.6 or later
	
	* The package depends on the JSON Smart library for JSON encoding and
	  decoding (this is a fork of the popular JSON.simple toolkit, but with
	  more efficent parsing). Its classes are conveniently included in the 
	  distributed JAR so you don't have to download and install it 
	  separately. JSON Smart is at http://code.google.com/p/json-smart/



Package content:

        README.txt                  This file.
        
        LICENSE.txt                 The software license.
	
	CHANGELOG.txt               The change log.
	
	jsonrpc2-base-{version}.jar JAR file containing the compiled package
	                            classes as well as the classes of the 
				    required JSON Smart package for JSON 
				    encoding and decoding.

	Example1.java               Example showing the complete life cycle of a 
	                            JSON-RPC 2.0 request.
				
	Example2.java               Example demonstrating how to parse a 
	                            JSON-RPC 2.0 message and determine its type 
				    - whether it's a request, notification or 
				    response.
	
	Example3.java               Example showing how to use the utility 
	                            classes to extract positional or named 
				    parameters from incoming requests with 
				    proper regard of type, and mandatory/optional 
				    values.
				  
	javadoc/                    The Java Docs for this package.
	
	build.xml                   The Apache Ant build file.
	
	lib/                        The package dependencies and their licenses.
        
	src/			    The source code for this package.
	
	test/                       JUnit tests for this package.




For complete JSON-RPC 2.0 Base documentation, examples and updates visit
	
	http://software.dzhuvinov.com/json-rpc-2.0-base.html


The JSON-RPC 2.0 specification and user group forum can be found at

	http://groups.google.com/group/json-rpc
	

The JSON Smart library is at

	http://code.google.com/p/json-smart/


[EOF]
