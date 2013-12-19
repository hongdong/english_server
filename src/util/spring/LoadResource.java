
package util.spring;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * <br>
 * <b>功能：</b>加载配置文件<br>
 */
public class LoadResource {
       private static Properties properties=new Properties();
       private static String config="/dataSourceConfig.properties"; 
       static{
    	     InputStream is=LoadResource.class.getClass().getResourceAsStream(config);
    	     try {
				properties.load(is);
				is.close();
				System.setProperties(properties);
				for(Map.Entry<Object, Object> entity:System.getProperties().entrySet()){
					System.out.println(entity.getKey()+":"+entity.getValue());
				}
				System.out.println(System.getProperty("jdbc.master.url"));
			} catch (IOException e) {
				e.printStackTrace();
			}
       }
       public LoadResource(){
    	   System.out.println("hello");
    	   InputStream is=LoadResource.class.getClass().getResourceAsStream(config);
       }
       public static void main(String[] args) {
    	   LoadResource sb=new LoadResource();
	   }
}

