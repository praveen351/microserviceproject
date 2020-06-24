package com.apigateway.filter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class PreFilter extends ZuulFilter {

	@Autowired
	EurekaClient discoveryClient;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();

		HttpServletRequest request = ctx.getRequest();
		Map<String, List<String>> datafetch = ctx.getRequestQueryParams();

		String msname = request.getRequestURL().toString().split(":")[2].split("/")[1].replace("ms", "micro")
				.toUpperCase();

		List<String> appname = new ArrayList<String>();
		int authport = 0;
		String appIP = "";
		for (Application application : discoveryClient.getApplications().getRegisteredApplications()) {
			for (InstanceInfo applicationsInstance : application.getInstances()) {
				appname.add(applicationsInstance.getAppName());
				if (applicationsInstance.getAppName().toLowerCase().equals("microauth")) {
					authport = applicationsInstance.getPort();
					appIP = applicationsInstance.getIPAddr();
				}
			}
		}
		if (appname.indexOf(msname) == -1) {
			ctx.setResponseStatusCode(500);
			if (ctx.getResponseBody() == null) {

				Map<String, Object> dataStatus = new HashMap<String, Object>();
				dataStatus.put("statusCode", 500);
				dataStatus.put("message", "The requested service is shutdown unfortunetly");
				dataStatus.put("date", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()));
				dataStatus.put("url", ctx.getRequest().getRequestURL().toString());

				ctx.setResponseBody(new Gson().toJson(dataStatus));
				ctx.getResponse().setContentType("application/json");
				ctx.setSendZuulResponse(false);
				return null;
			}
		}
		RestTemplate restTemplate = new RestTemplate();
		String ds = request.getRequestURL().toString();
		String[] urlarr = request.getRequestURL().toString().split(":");
		String url = urlarr[0].concat(":").concat(urlarr[1]).concat(":").concat(urlarr[2].split("/")[0]);
		if (!urlarr[2].split("/")[1].equals("msauth")) {
			if (datafetch != null && datafetch.size() != 0 && datafetch.get("authKey") != null) {
				String statUrl = "http://".concat(appIP).concat(":").concat(Integer.toString(authport))
						.concat("/autherize/chkAuthority/").concat(datafetch.get("authKey").get(0));
				Object response = restTemplate.getForObject(statUrl, Object.class);
				Map<String, Object> resAuth = (LinkedHashMap<String, Object>) response;
				if (!resAuth.get("status").equals("SUCCESS")) {

					Map<String, Object> dataStatus = new HashMap<String, Object>();
					dataStatus.put("statusCode", 401);
					dataStatus.put("message", "Unauthorized Persion");
					dataStatus.put("date", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()));
					dataStatus.put("url", ctx.getRequest().getRequestURL().toString());

					ctx.setResponseStatusCode(401);
					ctx.setResponseBody(new Gson().toJson(dataStatus));
					ctx.getResponse().setContentType("application/json");
					ctx.setSendZuulResponse(false);

					return null;
				}
			} else {
				
				Map<String, Object> dataStatus = new HashMap<String, Object>();
				dataStatus.put("statusCode", 404);
				dataStatus.put("message",
						"Please Provide proper Url or follow these example authKey keyValue i.e., authKey=FyfgyaswDWF57CzZSsa435");
				dataStatus.put("date", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()));
				dataStatus.put("url", ctx.getRequest().getRequestURL().toString());

				ctx.setResponseStatusCode(404);
				ctx.getResponse().setContentType("application/json");
				ctx.setResponseBody(new Gson().toJson(dataStatus));
				ctx.setSendZuulResponse(false);
				
				return null;
			}

		}
		return null;
	}
}
