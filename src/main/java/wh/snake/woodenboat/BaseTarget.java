
/**
 * @FILE:BaseTarget.java
 * @DATE:2014年4月18日 下午11:45:59
 **/
package wh.snake.woodenboat;

import wh.snake.corsair.Target;

/*******************************************
 * @CLASS:BaseTarget
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2014年4月18日 下午11:45:59
 *******************************************/
public class BaseTarget  implements Target{
	
	private String location;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}

