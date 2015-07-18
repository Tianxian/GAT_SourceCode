package cn.gateside.gattmg.generation;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.dom4j.DocumentException;

import cn.gateside.gattmg.infos.DataFileType;
import cn.gateside.gattmg.infos.ExecutorType;
import cn.gateside.gattmg.infos.TemplateInfos;
import cn.gateside.gattmg.util.DataFilesUtil;
import cn.gateside.gattmg.util.FileUtil;
import cn.gateside.gattmg.util.TemplateUtil;


public class TestClassGenerator {
	
	//获取datafiles全部文件名称作为TestClassName
	
	//获取各种所需文件内容
		//dataFileNames[overoverover]-----------DataFilesUtil.getPreNames
		//testClassNames[overoverover]----------DataFilesUtil.getTestClassNames
		//testMethodNames[overoverover]---------DataFilesUtil.getTestMethodNames
		//executorName[overoverover]------------DataFilesUtil.getExecutorName
		//executorParams[overoverover]----------DataFilesUtil.getExecutorParams
		//--[dataFileNames,testClassNames,testMethodNames]
	
	//读取生成TestClass模板
		//读取模板[overover]
		//分割模板[overover]
		//替换模板内容(testClassNames;testMethodNames;executorName;dataFileNames;[dataFileNames,testClassNames,testMethodNames])[overover]
		//写入模板内容到指定文件
	
	/**
	 * 根据传入参数内容，生成TestClass文件的内容
	 * @param executorName
	 * @param testClassName
	 * @param testMethodNames
	 * @param executorParams
	 * @return
	 * @throws Exception 
	 */
	public static String generateTestClassContent(String executorName, String testClassName, 
			List<String> testMethodNames, List<String> executorParams) throws Exception{
		String testString = null;
		StringBuilder stringBuilder = new StringBuilder();
		
		BufferedInputStream input = TemplateUtil.init(TemplateUtil.getTempPath() + "TestClassTmp");
		String srcStrings = FileUtil.fileToString(input);
		String[] splitedStrings = TemplateUtil.splitTestClassTmp(srcStrings, TemplateInfos.__TEST__);
		
		for (int i = 0; i < splitedStrings.length; i++) {
			splitedStrings[i] = TemplateUtil.replaceTemplateFile(splitedStrings[i], TemplateInfos.$TemplateClassName, testClassName);
			splitedStrings[i] = TemplateUtil.replaceTemplateFile(splitedStrings[i], TemplateInfos.$InterfaceStepExecutorName, executorName);	
			if( i == 1 ){
				testString = splitedStrings[i];
				for(int j = 0; j < testMethodNames.size(); j++){
					splitedStrings[i] = TemplateUtil.replaceTemplateFile(testString, TemplateInfos.$TemplateTestMethod, testMethodNames.get(j));
					splitedStrings[i] = TemplateUtil.replaceTemplateFile(splitedStrings[i], TemplateInfos.$ExecutorParam, executorParams.get(j));
					stringBuilder.append(splitedStrings[i]);
				}
				splitedStrings[i] = "";
			}
			stringBuilder.append(splitedStrings[i]);
		}
		return stringBuilder.toString();
	}
	
	/**
	 * 生成TestClass文件
	 * @param fileType
	 * @throws Exception 
	 */
	public static void generateTestClassFile(DataFileType fileType, String filePath) throws Exception{
		if(fileType == DataFileType.EXCEL)
		{
			createTestCaseClassFileForExcel(filePath);
		}
	    if(fileType == DataFileType.XML)
		{
			createTestCaseClassFileForXml(filePath, fileType,ExecutorType.InterfaceStepsExecutor);
		}
	    
	    if(fileType == DataFileType.WebUIXML)
		{
			createTestCaseClassFileForXml(filePath, fileType,ExecutorType.WebUIStepsExecutor);
		}
		
	}
	
	/**
	 * 
	 *        create testclass.java file for excel case
	 * @param filePath excel file path
	 * @throws Exception
	 */
	private static void createTestCaseClassFileForExcel(String filePath) throws Exception
	{
		List<String> dataFileNames = DataFilesUtil.getWholeNames(DataFileType.EXCEL);
		for(String eachFileName:dataFileNames)
		{
			String moduleName=eachFileName.substring(0,eachFileName.indexOf("."))+"_";
			List<String> sheetNames = DataFilesUtil.getTestClassNames(DataFileType.EXCEL, eachFileName);
			for(String eachSheet:sheetNames)
			{
				System.out.println(moduleName);
				System.out.println(eachSheet);
				List<String> testMethodNames = DataFilesUtil.getTestMethodNames(DataFileType.EXCEL, eachFileName, eachSheet);
				List<String> executorParams = DataFilesUtil.getExecutorParams(DataFileType.EXCEL, eachFileName, eachSheet);
				String contents = TestClassGenerator.generateTestClassContent(ExecutorType.InterfaceSingleStepExecutor.toString(),
						moduleName+eachSheet, testMethodNames, executorParams);
				FileUtil.createFile(filePath+moduleName, eachSheet + ".java", contents, true);
			}	
		}
	}
	
	/**
	 * 
	 * @param filePath xml file path
	 * @param fileType xml file type :WebUIXML,XML
	 * @param execturType   executurType
	 * @throws Exception
	 */
	private static void createTestCaseClassFileForXml(String filePath,DataFileType fileType,ExecutorType execturType) throws Exception
	{
		List<String> dataFileNames = DataFilesUtil.getWholeNames(fileType);
		for(String eachFileName:dataFileNames)
		{
            if(eachFileName.endsWith("TestCase.xml"))
            {
              String className=eachFileName.substring(0, eachFileName.lastIndexOf("."));
			List<String> testMethodNames = DataFilesUtil.getTestMethodNames(fileType, eachFileName, "");
			List<String> executorParams = DataFilesUtil.getExecutorParams(fileType, eachFileName, "");					
			String contents = TestClassGenerator.generateTestClassContent(execturType.toString(),
					className, testMethodNames, executorParams);
			FileUtil.createFile(filePath, className + ".java", contents, true);
            }	
		}
	}
    
}
