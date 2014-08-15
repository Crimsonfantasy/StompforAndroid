/** 
 * Classes to represent, parse and serialise JSON-RPC 2.0 requests, 
 * notifications and responses.
 *
 * <p>JSON-RPC is a protocol for 
 * <a href="http://en.wikipedia.org/wiki/Remote_procedure_call">remote 
 * procedure calls</a> (RPC) using <a href="http://www.json.org" >JSON</a>
 * - encoded requests and responses. It can be easily relayed over HTTP 
 * and is of JavaScript origin, making it ideal for use in dynamic web 
 * applications in the spirit of Ajax and Web 2.0.
 *
 * <p>This package implements <b>version 2.0</b> of the protocol, with the 
 * exception of <i>batching/multicall</i>. This feature was deliberately left
 * out as it tends to confuse users (judging by posts in the JSON-RPC forum).
 *
 * The JSON-RPC 2.0 specification and user group forum can be found 
 * <a href="http://groups.google.com/group/json-rpc">here</a>.
 *
 * <p><b>Package dependencies:</b> The classes in this package rely on the 
 * {@code net.minidev.json} and {@code net.minidev.json.parser} packages 
 * (version 1.0.9 and compabile) for JSON encoding and decoding. You can obtain
 * them from the <a href="http://code.google.com/p/json-smart/">JSON-Smart</a> 
 * website.
 *
 * @author Vladimir Dzhuvinov
 * @version 1.25.1 (2011-08-05)
 */
package com.thetransactioncompany.jsonrpc2;


  
