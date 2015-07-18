package cn.gateside.gattmg.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




import cn.gateside.gattmg.infos.TemplateInfos;

import com.gateside.autotesting.Gat.util.GlobalConfig;

public class TemplateUtil {

	/**
	 * 模板初始化函数，返回BufferedInputStream
	 * 
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 */
	public static BufferedInputStream init(String path)
			throws FileNotFoundException {
		FileInputStream finputStream = new FileInputStream(path);
		BufferedInputStream binputStream = new BufferedInputStream(finputStream);
		return binputStream;
	}
	
	/**
	 * 替换模板内需要替换的字符串
	 * @param srcString
	 * @param tmpInfo
	 * @param replaceString
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String replaceTemplateFile(String srcString, 
			TemplateInfos tmpInfo, String replaceString) throws Exception
			{
		     srcString = srcString.replace(tmpInfo.toString(), replaceString);
		return srcString;
	}
	
	/**
	 * 按照拆分字符拆分TestClass文件，返回拆分结果
	 * 
	 * @param fileString
	 * @param splitSign
	 * @return
	 */
	public static String[] splitTestClassTmp(String fileString, TemplateInfos tmpInfos) {
		String[] splitedString = null;
		if (fileString.contains(TemplateInfos.__TEST__.toString())) {
			splitedString = fileString.split(TemplateInfos.__TEST__.toString());
//			System.out.println(TemplateInfos.__TEST__.toString());
			return splitedString;
		}
		return splitedString;
	}
	
	/**
	 * 获取模板文件所在路径并返回
	 * @return
	 * @throws IOException
	 */
	public static String getTempPath() throws IOException{
		return FileUtil.getProjectDir() + GlobalConfig.getSlash()+"TemplateFiles"+GlobalConfig.getSlash();
	}
	
	/**
	 * 获取模板文件内容，并转化为String返回
	 * @param filePath
	 * @param fileName
	 * @return
	 * @throws IOException 
	 */
	public static String getTempFileString(String tmpFilePath, String tmpFileName) throws IOException{
		BufferedInputStream input = init(tmpFilePath + tmpFileName);
		String tmpStrings = FileUtil.fileToString(input);
		return tmpStrings;
	}
	
	/**
	 * 获取模板文件名，返回List
	 * @return
	 */
	public static List<String> tempFileNameList(){
		List<String> tempNameList = new ArrayList<String>();
		
//		tempNameList.add("ClasspathTmp");
//		tempNameList.add("ProjectTmp");
		/*---Modified by yanglei 2013-10-29 For 生成config文件---*/
//		tempNameList.add("GatConfigTmp");
//		tempNameList.add("LogConfigTmp");
		
//		tempNameList.add("SettingsTmp");
		tempNameList.add("TestClassTmp");
//		tempNameList.add("buildTmp");
		
		return tempNameList;
	}
}
