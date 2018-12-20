/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package com.dce.business.common.wxPay.util;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;

/**
 * This example demonstrates how to customize and configure the most common aspects
 * of HTTP request execution and connection management.
 */
public class HttpClientBuilder {

	
	public static CloseableHttpClient initHttpClient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException{

		SSLContext sslcontext = new SSLContextBuilder().loadTrustMaterial(null,
				new TrustStrategy() {
					// 信任所有
					public boolean isTrusted(X509Certificate[] chain,
							String authType) throws CertificateException {
						return true;
					}
				}).build();

		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
    			.register("http", PlainConnectionSocketFactory.INSTANCE)
    			.register("https", sslsf)
    			.build();
		
		CookieStore cookieStore = new BasicCookieStore();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		connManager.setMaxTotal(110);	
		
		 
		CloseableHttpClient httpclient = HttpClients.custom()
													.setSSLSocketFactory(sslsf)
													.setConnectionManager(connManager)
													.setDefaultCookieStore(cookieStore).build();

		
		return httpclient;

	}
	
	/**
	 * 采用代理发出 http request
	 * @return
	 * @throws Throwable
	 */
	public static CloseableHttpClient initHttpClientWithProxy() throws Throwable {
		//添加代理的header
		BasicHeader header = new BasicHeader(HttpProxy.ProxyAuthorization, HttpProxy.getAuthHeader());
        List<Header> list = new ArrayList<Header>();
        list.add(header);
        //end 添加代理的header
        
        HttpHost target = new HttpHost(HttpProxy.proxyIP, HttpProxy.proxyPort, null);
        
		SSLContext sslcontext = new SSLContextBuilder().loadTrustMaterial(null,
				new TrustStrategy() {
					// 信任所有
					public boolean isTrusted(X509Certificate[] chain,
							String authType) throws CertificateException {
						return true;
					}
				}).build();

		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
   			.register("http", PlainConnectionSocketFactory.INSTANCE)
   			.register("https", sslsf)
   			.build();
		
		CookieStore cookieStore = new BasicCookieStore();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		connManager.setMaxTotal(110);	
		
		 
		CloseableHttpClient httpclient = HttpClients.custom()
													.setSSLSocketFactory(sslsf)
													//.setDefaultCredentialsProvider(credsProvider)
													.setDefaultHeaders(list)
													.setProxy(target)
													.setConnectionManager(connManager)
													.setDefaultCookieStore(cookieStore).build();

		
		return httpclient;

	}

	
	
	public static HttpClientContext createHttpClientContext(){
		
		HttpClientContext localContext = HttpClientContext.create();
        return localContext;
	}
	
}