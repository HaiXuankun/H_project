package test;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class testXmlIO {
	public static void main(String[] args) {
	
		String xmlPath = "/ApplicationContext.xml";
		
		SAXReader reader = new SAXReader();
		InputStream ins = testXmlIO.class.getResourceAsStream(xmlPath);
		Document doc = null;
		Element ele = null;
		try {
			doc = reader.read(ins);
			ele = doc.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		List<Element> eleList = doc.selectNodes("//bean");
		System.out.println("eleList:" + eleList);
		for(Iterator i = ele.elementIterator("bean");i.hasNext();) {
			Element foo = (Element) i.next();
			
			String id = foo.attributeValue("id");//("id").getText();
			String cls = foo.attribute("class").getText();
			
			System.out.println("id:" + id + "=======class:" + cls);
		}
	}
	
}
