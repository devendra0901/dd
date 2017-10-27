package com.stackroute.deploymentdashboard.filter.error;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

public class ErrorFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public String filterType() {
        return "error";
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

    	RequestContext ctx= RequestContext.getCurrentContext();
    	HttpServletRequest request=ctx.getRequest();
    	if(request.getMethod().equalsIgnoreCase("post")) {
    		try{
    			System.out.println("............Error filter executed...."+request.getRequestURI()+"...."+IOUtils.toString(request.getReader()));
    		}catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    	if(request.getMethod().equalsIgnoreCase("get")) {
    		System.out.println("............Error filter executed...."+request.getRequestURI());
    	}
    	
        return null;
    }
}
