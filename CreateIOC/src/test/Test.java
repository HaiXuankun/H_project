package test;

import java.util.Map;
import java.util.Map.Entry;

import com.myspring.bean.Address;
import com.myspring.bean.User;
import com.myspring.config.Bean;
import com.myspring.config.xmlConfig;
import com.myspring.core.BeanFactory;
import com.myspring.core.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) {
        testIOC();
        //testConfig();
    }
    /**
     * 测试IOC容器
     */
    private static void testIOC(){

        BeanFactory bf = new ClassPathXmlApplicationContext("/ApplicationContext.xml");

        User user = (User) bf.getBean("user");
        System.out.println(user);
        System.out.println("address hashcode:"+user.getAddress().hashCode());

        Address address = (Address) bf.getBean("address");
        System.out.println(address);
        System.out.println("address hashcode:"+address.hashCode());
    }
    /**
     * 测试读取配置文件
     */
    private static void testConfig(){
        Map<String,Bean> map = xmlConfig.getConfig("/ApplicationContext.xml");
        for (Entry<String, Bean> entry : map.entrySet()) {
            System.out.println(entry.getKey()+"==="+entry.getValue());
        }
    }
}
