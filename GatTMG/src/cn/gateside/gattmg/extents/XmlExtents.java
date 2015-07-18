package cn.gateside.gattmg.extents;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


public class XmlExtents {
	
	/**
	 * ����Ԫ��element���������ƻ�ȡ��Ӧ���Ե�ֵ
	 * @param element
	 * @param attributeName
	 * @return
	 */
	public static List<String> getAttributeValue(Element element, String attributeName){
		List<String> attriValueList = new ArrayList<String>();
		for(@SuppressWarnings("unchecked")
		Iterator<Element> iter = element.elementIterator(); iter.hasNext();){
			Element e = (Element)iter.next();
			attriValueList.add(e.attributeValue(attributeName));
		}
		return attriValueList;
	}
	
	
	/**
	 * ����Ԫ�����ƻ�ȡ��Ӧ��element
	 * @param filePath
	 * @param elementName
	 * @return
	 * @throws DocumentException
	 */
	public static Element getElementByName(String filePath, String elementName) throws DocumentException{
		Element root = getRootElement(filePath);
		return root.element(elementName);
	}
	/**
	 * ����Ԫ�����ƻ�ȡ��Ӧ��element
	 * @param filePath
	 * @param elementName
	 * @return
	 * @throws DocumentException
	 */
	public static List<Element> getElementsByTag(String filePath, String tagName) throws DocumentException{
		Element root = getRootElement(filePath);
		return root.elements(tagName);
	}
	
	
	
	/**
	 * ��ȡxml�ļ��ĸ�Ԫ��
	 * @param filePath
	 * @return
	 * @throws DocumentException
	 */
	private static Element getRootElement(String filePath) throws DocumentException{
		SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File(filePath));
        Element root = document.getRootElement();
        return root;
	}
	
	/**
	 * ͨ������element���ƣ�������������ȡ��Ӧ������ֵ
	 * ����������ȡxml�ļ�����AllTestCases�ڵ��£����ж�ӦTestCase��ID��Name���Ե�ֵ
	 * @param filePath
	 * @param elementName
	 * @param attributeName
	 * @return
	 * @throws DocumentException
	 */
	public static List<String> getAttributeValueByElementName(String filePath, String elementName, String attributeName) throws DocumentException{
		List<String> valueList = new ArrayList<String>();
		List<Element> elementList = XmlExtents.getElementsByTag(filePath, elementName);
		for(Element item : elementList)
		valueList.add(item.attributeValue(attributeName));
		return valueList;
	}
	
	/**
	 * ���ݸ��ڵ��class name����ֵlist���ɶ�Ӧ��xml�ļ�
	 * @param rootName
	 * @param classStringNames
	 * @return
	 */
	public static Document createXml(String rootName, List<String> classStringNames){
		Document document = DocumentHelper.createDocument();
		
		Element root = document.addElement(rootName);		
		Element test = root.addElement("test");		
		Element classes = test.addElement("classes");		
		Element result = createClassesChild(classes, classStringNames);
		
		root.addAttribute("name","Suite");
		root.addAttribute("parallel", "none");
		test.addAttribute("name", "Test");
		
		return document;
	}
	
	/**
	 * ����testng.xml��classes�����ӽڵ�Ͷ�Ӧ��name����ֵ
	 * @param classElement
	 * @param testStrings
	 * @return
	 */
	private static Element createClassesChild(Element classElement, List<String> testStrings){
		Element eachElement = null;
		for(String each:testStrings){
			eachElement = classElement.addElement("class");
			eachElement.addAttribute("name", each);
		}
		return eachElement;
	}
	
	/**
	 * �������ɵ�xml�ļ�
	 * @return
	 */
	private static OutputFormat formatXmlFile(){
		OutputFormat xmlFormat = new OutputFormat();
		
		xmlFormat.setEncoding("UTF-8");
		xmlFormat.setIndentSize(2);
		xmlFormat.setNewlines(true);
		
		return xmlFormat;
	}
	
	/**
	 * ������ɵ�xml�ļ�
	 * @param document
	 * @param outputPath
	 * @param xmlName
	 * @throws IOException
	 */
	public static void XmlOutput(Document document, String outputPath, String xmlName) throws IOException{
		FileWriter fileWriter = new FileWriter(outputPath + xmlName);
		OutputFormat xmlFormat = formatXmlFile();
		XMLWriter xmlWriter = new XMLWriter(fileWriter, xmlFormat);
		xmlWriter.write(document);
		xmlWriter.close();
	}
}
