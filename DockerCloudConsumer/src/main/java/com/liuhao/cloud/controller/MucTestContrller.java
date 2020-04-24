package com.liuhao.cloud.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.liuhao.cloud.util.SimpleDateFormatUtil;

@RestController
public class MucTestContrller {
	
	private String secret = "2581bbbb7123d008";
	
	private String sysKey = "demo_sys";
	private String accessToken = "85e7f828-f8be-499e-a8e5-1f8d6471d275";
	
	private final String appKey = "a4ac7fb0";

	@SuppressWarnings("serial")
	@RequestMapping("/pushText")
    public String pushText(@RequestParam String token, 
    					@RequestParam(required=true) String content,
    					@RequestParam String groupid,
    					@RequestParam String userid) {
		
		HashMap<String,Object> signmap = new HashMap<>();
		
		Date date = new Date();
		long timestamp = date.getTime();
		
		token = StringUtils.isEmpty(token) ? accessToken : token;
		
		signmap.put("timestamp", timestamp);
		signmap.put("sysKey", sysKey);
		signmap.put("accessToken", token);
		
		String sign = getSign(signmap, secret);
		
		signmap.put("sign", sign);
		
		String url = buildUrlAttrs("http://10.18.18.87/mpm/v5/open/textmsg/push", signmap);
		System.out.println(url);
		
		HashMap<String,Object> datamap = new HashMap<>();
		datamap.put("appKey", appKey);
		datamap.put("filter", new HashMap<String,String>(){{put("group_id",groupid);put("user_id",userid);}});
		datamap.put("text", new HashMap<String,String>(){{put("content",content);}});
		datamap.put("msgtype", "text");
		
        String data= JSON.toJSONString(datamap);
        System.out.println(data);
        
        String result = "fail";
		
        try {
			result = sendMuc(url, data.getBytes("UTF-8"), "POST", "application/json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        System.out.println(result);
		return result;
	}
	
	@RequestMapping("/pushNotice")
    public String pushNotice(@RequestParam String token, 
    					@RequestParam(required=true) String content,
    					@RequestParam String groupid,
    					@RequestParam String userid) throws ParseException {
		
		String touser = userid;
		String toDeptNum = "";
		String sourceType = "todo";
		String realPush = "y";
		
		HashMap<String,Object> signmap = new HashMap<>();
		
		Date date = new Date();
		long timestamp = date.getTime();
		
		token = StringUtils.isEmpty(token) ? accessToken : token;
		
		signmap.put("timestamp", timestamp);
		signmap.put("sysKey", sysKey);
		signmap.put("accessToken", token);
		
		signmap.put("appKey", appKey);
		signmap.put("sourceType", sourceType);
		signmap.put("touser", touser);
		
		String sign = getSign(signmap, secret);
		
		signmap.put("sign", sign);
		
		String url = buildUrlAttrs("http://10.18.18.87/mpm/v5/open/templatemsg/createAndPush", signmap);
		System.out.println(url);
		
		String title = content;
		String noticeTitle = "ios标题";
		String summary = "摘要";
		String jumpUrl = "http://www.baidu.com";
		String shareRange = ""+2;
		String openType = ""+3;
		String coverImgUrl = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3386247472,87720242&fm=26&gp=0.jpg";
		String extras = "";
		String createTime = SimpleDateFormatUtil.formatDate(date);
		String mc_widget_identifier = "";
		String batchToUsers = "";
		
		HashMap<String,Object> datamap = new HashMap<>();
		
		datamap.put("title", title);
		datamap.put("noticeTitle", noticeTitle);		
		//datamap.put("shareRange", shareRange);
		//datamap.put("createTime", createTime);
		datamap.put("openType", openType);	
		datamap.put("summary", summary);
		//datamap.put("jumpUrl", jumpUrl);
		//datamap.put("coverImgUrl", null);
		
        String data= JSON.toJSONString(datamap);
        System.out.println(data);
        
        String result = "fail";
		
        try {
			result = sendMuc(url, data.getBytes("UTF-8"), "POST", "application/json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        System.out.println(result);
		return result;
	}

	@RequestMapping("/pushImage")
    public String pushImage(@RequestParam String token, 
    					@RequestParam(required=true) String content,
    					@RequestParam String groupid,
    					@RequestParam String userid) {
		
		HashMap<String,Object> signmap = new HashMap<>();
		
		Date date = new Date();
		long timestamp = date.getTime();
		
		token = StringUtils.isEmpty(token) ? accessToken : token;
		
		signmap.put("timestamp", timestamp);
		signmap.put("sysKey", sysKey);
		signmap.put("accessToken", token);
		
		String sign = getSign(signmap, secret);
		
		signmap.put("sign", sign);
		
		String url = buildUrlAttrs("http://10.18.18.87/mpm/v5/open/image/push", signmap);
		System.out.println(url);
		
		HashMap<String,Object> datamap = new HashMap<>();
		datamap.put("appKey", appKey);
		datamap.put("filter", new HashMap<String,String>(){{put("group_id",groupid);put("user_id",userid);}});
		datamap.put("imageurl", "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3386247472,87720242&fm=26&gp=0.jpg");
		
        String data= JSON.toJSONString(datamap);
        System.out.println(data);
        
        String result = "fail";
		
        try {
			result = sendMuc(url, data.getBytes("UTF-8"), "POST", "application/json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        System.out.println(result);
		return result;
	}
	
	private String getSign(Map<String,Object> map, String secret) {
		// 第一步：检查参数是否已经排序
		String[] keys = map.keySet().toArray(new String[0]);
		Arrays.sort(keys);

	    // 第二步：把所有参数名和参数值串在一起
	    StringBuilder query = new StringBuilder();
	
	    query.append(secret);
	    for (String key : keys) {
	        String value = String.valueOf(map.get(key));
	        query.append(key).append(value);
		}
    	query.append(secret);
    	// 第三步：计算签名
    	String sign = org.springframework.util.DigestUtils.md5DigestAsHex(query.toString().getBytes());

    	return sign;
	}
	
	private String sendMuc(String url, byte[] data, String method, String contentType) throws IOException {
		
		HttpURLConnection conn = null;
		URL httpUrl = null;
		OutputStream out = null;
		
		httpUrl = new URL(url);
		conn = (HttpURLConnection) httpUrl.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);	
		conn.setRequestMethod(method);
		conn.setConnectTimeout(10 * 1000);
		if (contentType != null)
			conn.setRequestProperty("Content-Type", contentType);
		
		if(data != null && data.length != 0){
			out = conn.getOutputStream();
			out.write(data);	
		}
			
		conn.connect();
		InputStream in = conn.getInputStream();
		String response = read(new InputStreamReader(in, "UTF-8"));
		
		in.close();
		return response;
	}
	
	private String read(Reader in) throws IOException {
		BufferedReader reader = new BufferedReader(in);
		StringBuilder response = new StringBuilder();
		char[] buf = new char[64];
		int len = 0;
		while((len = reader.read(buf)) > 0) {
			response.append(buf, 0, len);
		}
		
		return response.toString();
	}
	
	@SuppressWarnings("unused")
	private String buildUrlAttrs(String url, Map<String, Object> attrs) {
		StringBuilder urlAttrs = new StringBuilder();
		urlAttrs.append(url);
		urlAttrs.append("?");
		Set<String> keys = attrs.keySet();
		for(String key : keys) {
			String value = attrs.get(key).toString();
			urlAttrs.append(key + "=" + value + "&");
		}
		urlAttrs.deleteCharAt(urlAttrs.length() - 1);
		return urlAttrs.toString();
	}
	
}
