
/**
 * @FILE:CstoPirate.java
 * @DATE:2014年4月20日 下午10:19:11
 **/
package corsair.csto;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wh.snake.corsair.Belongings;
import wh.snake.woodenboat.html.HtmlPirate;

/*******************************************
 * @CLASS:CstoPirate
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2014年4月20日 下午10:19:11
 *******************************************/
public class CstoPirate extends HtmlPirate {
	
	private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
	private static final String FIELD = ",";
	private static final String REGEX_LEVEL_1 = "<dl>.*?</dl>";
	private static final String REGEX_LEVEL_2_PRICE = "<span class=\"price\" title=\"项目预算：￥([0-9,]+)-￥([0-9,]+)\">";
	private static final String REGEX_LEVEL_2_PRICE_MAX = "<span class=\"price\" title=\"项目预算：大于￥([0-9,]+)\">";
	private static final String REGEX_LEVEL_2_PRICE_MIN = "<span class=\"price\" title=\"项目预算：小于￥([0-9,]+)\">";
	private static final String REGEX_LEVEL_2_TITLE = "<div class=\"title\">\\s*<a href=\"(\\S+)\" target=\"_blank\">\\s*(.+?)\\s*</a>";
	private static final String REGEX_LEVEL_2_TYPE = "<span class=\"classfiy\"><a href=\"\\S+\" title=\"(\\S+)\">";
	private static final String REGEX_LEVEL_2_DATE = "<li>发布日期:\\s*([0-9-]+)\\s*</li>";
	@Override
	public Belongings harvest(int status, Map<String, String> headers,
			String body) {
		
		if( status != 200 ){
			logger.warn("status code is " + status + " not 200");
			return null;
		}
		
		if( StringUtils.isEmpty(body) ){
			logger.warn("body is empty");
			return null;
		}
		Pattern pattern1 = Pattern.compile(REGEX_LEVEL_1);
		Pattern pattern2Price = Pattern.compile(REGEX_LEVEL_2_PRICE);
		Pattern pattern2PriceMax = Pattern.compile(REGEX_LEVEL_2_PRICE_MAX);
		Pattern pattern2PriceMin = Pattern.compile(REGEX_LEVEL_2_PRICE_MIN);
		Pattern pattern2Title = Pattern.compile(REGEX_LEVEL_2_TITLE);
		Pattern pattern2Type = Pattern.compile(REGEX_LEVEL_2_TYPE);
		Pattern pattern2Date = Pattern.compile(REGEX_LEVEL_2_DATE);
		Matcher matcher1 = pattern1.matcher(body);
		StringBuilder sb = new StringBuilder();
		while( matcher1.find() ){
			String url = null;
			String content1 = matcher1.group();
			logger.debug("level1 string is " +content1);
			Matcher m = null;
			// title
			m = pattern2Title.matcher(content1);
			if( m.find() ){
				url = m.group(1);
				sb.append(m.group(2).replaceAll(FIELD, "/")).append(FIELD); 
			} else{
				continue;
			}
			// type
			m = pattern2Type.matcher(content1);
			if( m.find() ){
				sb.append(m.group(1)).append(FIELD); 
			} else{
				sb.append("unknow").append(FIELD); 
			}
			
			// price
			m = pattern2Price.matcher(content1);
			if( m.find() ){
				sb.append(m.group(1).replaceAll(",", "")).append(FIELD).append(m.group(2).replaceAll(",", ""));
			} else{
				m = pattern2PriceMin.matcher(content1);
				if( m.find() ){
					sb.append(0).append(FIELD).append(m.group(1).replaceAll(",", ""));
				} else {
					m = pattern2PriceMax.matcher(content1);
					if(  m.find() ){
						sb.append(m.group(1).replaceAll(",", "")).append(FIELD).append(m.group(1).replaceAll(",", ""));
					}
				}
			}
			
			// date
			m = pattern2Date.matcher(content1);
			if( m.find() ){
				sb.append(FIELD).append(m.group(1)); 
			} else{
				sb.append(FIELD).append("1900-01-01"); 
			}
			
			// url
			sb.append(FIELD).append(url);
			
			logger.info(sb.toString());
			
			sb.append(System.getProperty("line.separator"));
		}
		Belongings belongings = new Belongings();
		belongings.setValue(sb.toString());
		return belongings;
	}
}

