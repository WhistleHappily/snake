
/**
 * @FILE:Snake.java
 * @AUTHOR:sulei
 * @DATE:2014年4月17日 下午10:51:53
 **/
package wh.snake;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.helpers.Charsets;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import corsair.csto.CstoPirate;
import wh.snake.corsair.Belongings;
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
        byte[] bom ={(byte) 0xEF,(byte) 0xBB,(byte) 0xBF};
        File csv = new File("D:\\develop\\csv\\csto.csv");
        FileOutputStream fos = null;
        try{
        	if( !csv.exists() ){
        		csv.createNewFile();	
        	}
        	fos = new FileOutputStream(csv);
        	fos.write(bom);// to resolve the chatsets
        } catch(Exception e){
			logger.error(e);
			return;
        }
        HtmlPirate pirate = new CstoPirate();
        HtmlTarget target = new HtmlTarget();
        int page = 0;
        while( page++ < 10 ){
        	target.setLocation("http://www.csto.com/project/list?page=" + page);
        	Belongings belongings = pirate.attack(target);
        	if( belongings != null  && !StringUtils.isEmpty(belongings.getValue())){
        		try {
					fos.write(belongings.getValue().getBytes());
					Thread.sleep(3000);
				} catch (Exception e) {
					logger.error(e);
				}
        	}
        }
        
        try {
			fos.close();
		} catch (IOException e) {
			logger.error(e);
		}
    }

}

