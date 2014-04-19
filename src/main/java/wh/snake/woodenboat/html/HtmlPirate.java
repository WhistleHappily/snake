
/**
 * @FILE:HtmlPirate.java
 * @DATE:2014年4月18日 下午11:43:21
 **/
package wh.snake.woodenboat.html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wh.snake.corsair.Belongings;
import wh.snake.corsair.Pirate;
import wh.snake.corsair.Target;

/*******************************************
 * @CLASS:HtmlPirate
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2014年4月18日 下午11:43:21
 *******************************************/
public class HtmlPirate implements Pirate {
	
	private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
	
	private CloseableHttpClient httpClient = HttpClients.createDefault();
	
	private HtmlTarget currentTarget = null;
	
	@Override
	public Belongings attack(Target target) {
		
		if( target == null ){
			logger.warn("The target cann't be null");
			return null;
		} else if( target instanceof HtmlTarget ){
			this.currentTarget = (HtmlTarget)target;
		} else{
			logger.warn("The target's class must be HtmlTarget but actually is " + target.getClass());
			return null;
		}
		
		HttpGet httpGet = new HttpGet(currentTarget.getLocation());
		CloseableHttpResponse response = null;
		Belongings belongings = null;
		String line = null;
		StringBuilder content = new StringBuilder();
		try {
			response = httpClient.execute(httpGet);
			if( response == null ){
				return belongings;
			}
		} catch (ClientProtocolException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		
		// status code
		logger.debug(response.getStatusLine());
		
		// headers
		Map<String, String> headers = new HashMap<String, String>();
		Header[] hs = response.getAllHeaders();
		if( !ArrayUtils.isEmpty(hs) ){
			for(Header h : hs ){
				logger.debug("[header]" + h);
				headers.put(h.getName(), h.getValue());
			}
		}
		// body
		HttpEntity entity = response.getEntity();
		if( entity != null ){
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
				while( (line = reader.readLine()) != null ){
					content.append(line);
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
	
		return harvest(response.getStatusLine().getStatusCode()
				,headers
				,content.toString());
	}
	
	public Belongings harvest(int status, Map<String, String> headers, String body){
		return null;
	}
}

