
/**
 * @FILE:Snake.java
 * @AUTHOR:sulei
 * @DATE:2014年4月17日 下午10:51:53
 **/
package wh.snake;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import corsair.csto.CstoPirate;
import wh.snake.woodenboat.html.HtmlPirate;
import wh.snake.woodenboat.html.HtmlTarget;

/*******************************************
 * @COMPANY:深圳市鼎火科技有限责任公司
 * @CLASS:Snake
 * @DESCRIPTION:	
 * @AUTHOR:sulei
 * @VERSION:v1.0
 * @DATE:2014年4月17日 下午10:51:53
 *******************************************/
public class Snake
{
    private Logger logger = LogManager.getLogger(this.getClass());
    
    public static void main(String[] args)
    {
//        ApplicationContext applicationContext = 
//                new ClassPathXmlApplicationContext("application.xml");
        
        Snake snake = new Snake();
        
        snake.start();
    }
    
    /**
     * @description: start to eat elephas
     */
    private void start(){
        logger.info("Begin");
        HtmlPirate pirate = new CstoPirate();
        HtmlTarget target = new HtmlTarget();
        target.setLocation("http://www.csto.com/project/list");
        pirate.attack(target);
    }

}

