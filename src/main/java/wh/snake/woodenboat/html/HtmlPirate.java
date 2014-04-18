
/**
 * @FILE:HtmlPirate.java
 * @DATE:2014年4月18日 下午11:43:21
 **/
package wh.snake.woodenboat.html;

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
	
	@Override
	public Belongings attack(Target target) {
		if( target == null ){
			logger.warn("The target cann't be null");
			return null;
		} else if( target instanceof HtmlTarget ){
			target = (HtmlTarget)target;
		} else{
			logger.warn("The target's class must be HtmlTarget but actually is " + target.getClass());
			return null;
		}
		return null;
	}
}

