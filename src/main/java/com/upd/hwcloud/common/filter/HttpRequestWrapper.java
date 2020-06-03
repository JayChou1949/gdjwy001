package com.upd.hwcloud.common.filter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrap the generic httpServletRequest
 */
public class HttpRequestWrapper extends HttpServletRequestWrapper {
	
	private String body;
	
	private ServletInputStream inputStream;
	
	private BufferedReader reader;
	
	private String requestURI;
	
	private StringBuilder requestURL;
	
	private String servletPath;
	
	private Map<String, ?> params;
	
	public HttpRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		if (!isMultipart()) {
			preLoadBody(request);
        }
	}

	private void preLoadBody(HttpServletRequest request) throws IOException {
		Charset charset = Charset.forName(getCharacterEncoding());
        byte[] bodyBytes = IOUtils.toByteArray(request.getInputStream());
        body = new String(bodyBytes, charset);
        inputStream = new RequestCachingInputStream(body.getBytes(getCharacterEncoding()));
    }
	
	public final boolean isMultipart() {
        String contentType = getContentType();
        return contentType != null && contentType.toLowerCase().startsWith("multipart/");
    }
	
	@Override
	public final String getContentType() {
		String contentType = getParameter("_contentType");
		if (contentType != null) {
			return contentType;
		}
        return super.getContentType();
	}

	public String getBody() {
        if (isMultipart()) {
        	throw new IllegalStateException("multipart request does not support preloaded body");
        }
        return body;
    }

	@Override
	public String getMethod() {
		String method = getParameter("_method");
        if (method != null) {
        	return method;
        }
        return super.getMethod();
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (inputStream != null) {
			return inputStream;
		}
        return super.getInputStream();
	}

	@Override
	public BufferedReader getReader() throws IOException {
		if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(inputStream, getCharacterEncoding()));
        }
        return reader;
	}
	
	@Override
    public final String getCharacterEncoding() {
        String defaultEncoding = super.getCharacterEncoding();
        return defaultEncoding != null ? defaultEncoding : "UTF-8";
    }
	
	public void setParams(Map<String, ?> params) {
		this.params = params;
	}
	/**
	 * used to cache the request inputstream
	 * @author sylorl
	 * @date Mar 10, 2015
	 */
	private static class RequestCachingInputStream extends ServletInputStream {
		
        private final ByteArrayInputStream inputStream;

        public RequestCachingInputStream(byte[] bytes) {
            inputStream = new ByteArrayInputStream(bytes);
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

		@Override
		public boolean isFinished() {
			return false;
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setReadListener(ReadListener readListener) {
			
		}
    }

	@Override
	public String getRequestURI() {
		if(this.requestURI == null) {
			return super.getRequestURI();
		}
		return super.getRequestURI();
	}

	@Override
	public StringBuffer getRequestURL() {
		if(this.requestURL == null) {
			return super.getRequestURL();
		}
		return new StringBuffer(this.requestURL.toString());
	}

	@Override
	public String getServletPath() {
		if(servletPath == null) {
			return super.getServletPath();
		}
		return this.servletPath;
	}

	public void setRequestURI(String requestURI, HttpServletRequest request) {
		this.servletPath = requestURI;
		this.requestURI = request.getContextPath() + requestURI;
		this.requestURL = new StringBuilder().append(request.getProtocol())
											.append("://")
											.append(request.getLocalAddr())
											.append("/")
											.append(servletPath);
	}
	
	
	public String[] getParameterValues(String name) {
		if(params != null) {
			Object v = params.get(name);
	        if (v==null) {
	            return null;
	        } else if(v instanceof String[]) {
	            return (String[]) v;
	        } else if(v instanceof String) {
	            return new String[]{(String) v};
	        } else {
	            return new String[]{v.toString()};
	        }
		}
		return super.getParameterValues(name);
    }
    
    public String getParameter(String name) {
    	if(params != null) {
    		Object v = params.get(name);
            if(v == null) {
                return null;
            } else if(v instanceof String[]) {
                String []strArr = (String[]) v;
                if(strArr.length > 0){
                    return strArr[0];
                } else {
                    return null;
                }
            } else if(v instanceof String) {
                return (String) v;
            } else {
                return v.toString();
            }
    	} 
    	return super.getParameter(name);
    }
    
	public Map<String, String[]> getParameterMap() {
    	if(params != null){
    		Map<String, String[]> map = new HashMap<String, String[]>();
    		for(Object key:params.keySet()){
    			 Object v = params.get((String)key);
    			 String[] strArr = null;
    			 if(v == null){
    				 return null;
    			 } else if(v instanceof String[]) {
    				 strArr = (String[]) v;
    			 } else if(v instanceof String) {
    				 strArr = new String[1];
    				 strArr[0] = (String) v;
    		     } else {
    		    	 strArr = new String[1];
    				 strArr[0] = v.toString();
    		     }
    			 map.put((String)key, strArr);
    		}
    		return map;
    	}
        return super.getParameterMap();
    }
	
	public String getParameters() {
		StringBuilder stringBuilder=new StringBuilder();
		Enumeration<String> parameterNames = getParameterNames();
		if(parameterNames!=null) {
			while(parameterNames.hasMoreElements()) {
				String nextElement = parameterNames.nextElement();
				stringBuilder.append(nextElement);
				stringBuilder.append("=");
				stringBuilder.append(getParameter(nextElement));
				stringBuilder.append("&");
			}
			if(StringUtils.isNotBlank(stringBuilder.toString())) {
				return stringBuilder.substring(0, stringBuilder.length()-1);
			}
		}
		return null;
	}
}
