package com.dce.business.common.wxPay.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.sf.json.JSONObject;



/**
 * 发送后台http请求
 * @author hufeng
 *
 */
public class HttpTool {
	 
	private static final Logger log = LogManager.getLogger(HttpTool.class);
	/**
	 * 发送post请求
	 * @param url 请求url
	 * @param paramMap 参数Map
	 * @return 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String post(String url, Map<String, Object> paramMap) throws ClientProtocolException, IOException {
	
		StringBuffer buf = new StringBuffer();
		buf.append("<xml>");
		for (Entry<?, ?> e : paramMap.entrySet()) {
			buf.append("<").append(e.getKey()).append(">")
			   .append(e.getValue())
			   .append("</").append(e.getKey()).append(">");
		}
		buf.append("</xml>");
		
		if(log.isDebugEnabled()) {
			log.debug("发送微信报文："+buf.toString());
		}
		log.info("发送微信报文："+buf.toString());
		 String weixinPost = httpsRequest(url, "POST", buf.toString()).toString();
	
//		JSONObject jsobj = WeixinUtil.httpRequest(url, "POST", buf.toString());
		CloseableHttpClient httpClient = createHttpClient();
		HttpPost post = new HttpPost(url);
        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(buf.toString(), "UTF-8");
        post.addHeader("Content-Type", "text/xml");
        post.setEntity(postEntity);
		
		CloseableHttpResponse response = httpClient.execute(post);
		
		return EntityUtils.toString(response.getEntity(), "UTF-8");
	}

	
	private static CloseableHttpClient createHttpClient() {
		
		X509TrustManager x509mgr = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}
			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
	    };
	
	    SSLContext sslContext = null;
		try {
			sslContext = SSLContexts.createDefault();
			sslContext.init(null, new TrustManager[] { x509mgr }, null);
		}  catch (KeyManagementException e1) {
			throw new RuntimeException(e1);
		}
	    
		RequestConfig config = RequestConfig.custom().setStaleConnectionCheckEnabled(true)
				.setConnectTimeout(30000)
				.setSocketTimeout(30000)
				.setConnectionRequestTimeout(1500)
				.build();
		
		CloseableHttpClient client = HttpClients.custom()
				.setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
				.setSslcontext(sslContext)
				.setDefaultRequestConfig(config)
				.build();
		
		return client;
	}
	
	public static Map<String, String> xml2Map(String xml) throws DocumentException {
		Document doc = DocumentHelper.parseText(xml);
		Element root = doc.getRootElement();
		List<?> elements = root.elements();
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < elements.size(); i++) {
			Element e = (Element)elements.get(i);
			map.put(e.getName(), e.getText());
		}
		return map;
	}
	
	 public static String xmlHttpProxy(String url,String xml,String method,String contentType){
	        InputStream is = null;
	        OutputStreamWriter os = null;

	        try {
	            URL _url = new URL(url);
	            HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
	            conn.setDoInput(true);   
	            conn.setDoOutput(true);   
	            conn.setRequestProperty("Content-type", "text/xml");
	            conn.setRequestProperty("Pragma:", "no-cache");  
	            conn.setRequestProperty("Cache-Control", "no-cache");  
	            conn.setRequestMethod("POST");
	            os = new OutputStreamWriter(conn.getOutputStream());
	            os.write(new String(xml.getBytes(contentType)));
	            os.flush();

	            //返回值
	            is = conn.getInputStream();
	            return getContent(is, "utf-8");
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally{
	            try {
	                if(os!=null){os.close();}
	                if(is!=null){is.close();}
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return null;
	    }
	 
	 /**
	     * 解析返回的值
	     * @param is
	     * @param charset
	     * @return
	     */
	    public static String getContent(InputStream is, String charset) {
	        String pageString = null;
	        InputStreamReader isr = null;
	        BufferedReader br = null;
	        StringBuffer sb = null;
	        try {
	            isr = new InputStreamReader(is, charset);
	            br = new BufferedReader(isr);
	            sb = new StringBuffer();
	            String line = null;
	            while ((line = br.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	            pageString = sb.toString();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (is != null){
	                    is.close();
	                }
	                if(isr!=null){
	                    isr.close();
	                }
	                if(br!=null){
	                    br.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            sb = null;
	        }
	        return pageString;
	    }
	    
	    /**
	     * post请求并得到返回结果
	     * @param requestUrl
	     * @param requestMethod
	     * @param output
	     * @return
	     */
	    public static String httpsRequest(String requestUrl, String requestMethod, String output) {
	        try{
	            URL url = new URL(requestUrl);
	            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
	            connection.setDoOutput(true);
	            connection.setDoInput(true);
	            connection.setUseCaches(false);
	            connection.setRequestMethod(requestMethod);
	            if (null != output) {
	                OutputStream outputStream = connection.getOutputStream();
	                outputStream.write(output.getBytes("UTF-8"));
	                outputStream.close();
	            }
	            // 从输入流读取返回内容
	            InputStream inputStream = connection.getInputStream();
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	            String str = null;
	            StringBuffer buffer = new StringBuffer();
	            while ((str = bufferedReader.readLine()) != null) {
	                buffer.append(str);
	            }
	            bufferedReader.close();
	            inputStreamReader.close();
	            inputStream.close();
	            inputStream = null;
	            connection.disconnect();
	            return buffer.toString();
	        }catch(Exception ex){
	            ex.printStackTrace();
	        }

	        return "";
	    }
}
