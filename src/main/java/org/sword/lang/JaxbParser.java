/**
 * 
 */
package org.sword.lang;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author ChengNing
 * @date   2014年12月7日
 */
public class JaxbParser {
	
	private static Logger logger = Logger.getLogger(JaxbParser.class);
	
	private Class clazz;
	private String[] cdataNode;
	private String config="utf-8";

	/**
	 * 
	 * @param clazz
	 */
	public JaxbParser(Class clazz){
		this.clazz = clazz;
	}
	
	/**
	 * 设置需要包含CDATA的节点
	 * @param cdataNode
	 */
	public void setCdataNode(String[] cdataNode) {
		this.cdataNode = cdataNode;
	}

	/**
	 * 转为xml串
	 * @param obj
	 * @return
	 */
	public String toXML(Object obj){
		String result = null;
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller m = context.createMarshaller();

//			String encoding = Config.instance().getJaxb_encoding();
			String encoding = config;
			logger.debug("toXML encoding " + encoding + "System file.encoding " + System.getProperty("file.encoding"));

			m.setProperty(Marshaller.JAXB_ENCODING, encoding);
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setProperty(Marshaller.JAXB_FRAGMENT, true);// 去掉报文头

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			XMLSerializer serializer = getXMLSerializer(os);

			m.marshal(obj, serializer.asContentHandler());

			result = os.toString(encoding);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("response text:" + result);
		return result;

	}
	

	/**
	 * 转为对象
	 * @param is
	 * @return
	 */
	public Object toObj(InputStream is){
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(clazz);
			Unmarshaller um = context.createUnmarshaller();
			Object obj = um.unmarshal(is);
			return obj;
		} catch (Exception e) {
			logger.error("post data parse error");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * XML转为对象
	 * @param xmlStr
	 * @return
	 */
	public Object toObj(String xmlStr){
		InputStream is = new ByteArrayInputStream(xmlStr.getBytes());
		return toObj(is);
	}
	
	/**
	 * 设置属性
	 * @param os
	 * @return
	 */
	private XMLSerializer getXMLSerializer(OutputStream os) {
		OutputFormat of = new OutputFormat();
		formatCDataTag();
		of.setCDataElements(cdataNode);
		of.setPreserveSpace(true);
		of.setIndenting(true);
		of.setOmitXMLDeclaration(true);

		String encoding = config;
		of.setEncoding(encoding);
		XMLSerializer serializer = new XMLSerializer(of);
		serializer.setOutputByteStream(os);
		return serializer;
	}


	/**
	 * 适配cdata tag
	 */
	private void formatCDataTag(){
		for(int i=0;i<cdataNode.length;i++){
			cdataNode[i] = "^" + cdataNode[i];
		}
	}
}
