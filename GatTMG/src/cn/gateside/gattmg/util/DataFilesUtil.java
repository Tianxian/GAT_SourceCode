package cn.gateside.gattmg.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;





import cn.gateside.gattmg.extents.ExcelExtents;
import cn.gateside.gattmg.extents.XmlExtents;
import cn.gateside.gattmg.infos.DataFileType;
import cn.gateside.gattmg.infos.ExecutorType;

import com.gateside.autotesting.Gat.util.GlobalConfig;

public class DataFilesUtil {
	
	/**
	 * ͨ�������DataFile�ļ����ͷ��ض�ӦDataFile���ļ�·��
	 * @param fileType
	 * @return
	 * @throws IOException
	 */
	public static String getDataFilesPath(DataFileType fileType) throws IOException{
		String dataFilePath = null;
		
		if(fileType == DataFileType.EXCEL)
		{
			dataFilePath = ProjectUtil.getProjectBasePath() + GlobalConfig.getSlash()+"DataFiles"+GlobalConfig.getSlash()+"Excels";
		}
		else
		{
			dataFilePath = ProjectUtil.getProjectBasePath() + GlobalConfig.getSlash()+"DataFiles"+GlobalConfig.getSlash()+"Xmls";
		}
		return dataFilePath;
	}
	
	/**
	 * ���ݴ����DataFile�ļ����ͻ�ȡ��ӦDataFile·���µ������ļ���
	 * @param fileType
	 * @return
	 * @throws IOException
	 */
	public static List<String> getWholeNames(DataFileType fileType) throws IOException{
		String dataFilePath = null;
		List<String> allFileNames = null;
		
		if(fileType == DataFileType.EXCEL)
		{
			dataFilePath = getDataFilesPath(fileType);
			allFileNames = FileUtil.getFilesName(dataFilePath);
		}else
		{
			dataFilePath = getDataFilesPath(fileType);
			allFileNames = FileUtil.getFilesName(dataFilePath);
		}
		return allFileNames;
	}
	
	/**
	 * ���ݴ����DataFile�ļ����ͻ�ȡ��ӦDataFile·���µ������ļ���[�������ļ�����׺]
	 * @param fileType
	 * @return
	 * @throws IOException
	 */
	public static List<String> getPreNames(DataFileType fileType) throws IOException{
		String dataFilePath = null;
		List<String> wholeFileNames = null;
		List<String> preFileNames = new ArrayList<String>();
		
		if(fileType == DataFileType.EXCEL){
			dataFilePath = getDataFilesPath(fileType);
			wholeFileNames = FileUtil.getFilesName(dataFilePath);
			for(int j=0; j<wholeFileNames.size(); j++){
				String eachPreName = wholeFileNames.get(j).substring(0, wholeFileNames.get(j).lastIndexOf("."));
				preFileNames.add(eachPreName);
			}
			return preFileNames;
		}else if(fileType == DataFileType.XML){
			dataFilePath = getDataFilesPath(fileType);
			wholeFileNames = FileUtil.getFilesName(dataFilePath);
			for(int j=0; j<wholeFileNames.size(); j++){
				String eachPreName = wholeFileNames.get(j).substring(0, wholeFileNames.get(j).lastIndexOf("."));
				preFileNames.add(eachPreName);
			}
			return preFileNames;
		}else{
			return preFileNames;
		}
	}
	
	/**
	 * ���ݴ����ļ����ͺ��ļ�������ȡ��ӦDataFile�ļ���TestClassName
	 * Excel: TestClassName = ExcelFile sheetName
	 * XML: TestClassName = AllTestCases.TestCase.Name����
	 * @param fileType
	 * @param eachFileName
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static List<String> getTestClassNames(DataFileType fileType, String eachFileName) throws IOException, DocumentException{
		List<String> classNameList = new ArrayList<String>();
		String dataFilePath = null;
		
		if(fileType == DataFileType.EXCEL)
		{
			dataFilePath = getDataFilesPath(fileType) +GlobalConfig.getSlash() + eachFileName;
			//��ȡÿ���ļ���sheet��(excel��sheet��=testClassName)
			classNameList = ExcelExtents.getSheetNameList(dataFilePath);
		}
		else
		{
			if(eachFileName.endsWith("TestCase.xml"))
			{
			  String className=eachFileName.substring(0, eachFileName.indexOf("."));
			  classNameList.add(className);
			}
		}
		return classNameList;
	}
	

	
	/**
	 * ���ݴ���DataFile�ļ����ͣ����ƻ�ȡ��ָ���ļ����е�testMethod��
	 * Excel:	��Ҫ����sheetName��fileName
	 * XML��		����Ҫ����sheetName�ֶ�Ϊ�վ���
	 * @param fileType
	 * @param eachFileName
	 * @param fileSheetName
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static List<String> getTestMethodNames(DataFileType fileType, String eachFileName, String fileSheetName) throws IOException, DocumentException{
		List<String> methodNameList = new ArrayList<String>();
		String dataFilePath = null;
		
		if(fileType == DataFileType.EXCEL)
		{
			dataFilePath = getDataFilesPath(fileType) +GlobalConfig.getSlash()+ eachFileName;
			Integer rowCounts = ExcelExtents.getSheetRowCounts(dataFilePath, fileSheetName);
			for(Integer i=1; i<rowCounts; i++)
			{
				Object rowObject = null;
				rowObject = ExcelExtents.getSheetRow(dataFilePath, fileSheetName, i);
				methodNameList.add(ExcelExtents.getFirstColElement(dataFilePath, rowObject));
			}
		}
		else
		{
			if(eachFileName.endsWith("TestCase.xml"))
			{
			   dataFilePath = getDataFilesPath(fileType) + GlobalConfig.getSlash()+ eachFileName;
			   methodNameList = XmlExtents.getAttributeValueByElementName(dataFilePath, "TestCase", "Name");
			}
		}
		return methodNameList;
	}
	
	public static List<String> getTestCaseID(DataFileType fileType, String eachFileName, String fileSheetName) throws IOException, DocumentException{
		List<String> caseIDList = new ArrayList<String>();
		String dataFilePath = null;
	    if(eachFileName.endsWith("TestCase.xml"))
		{
			   dataFilePath = getDataFilesPath(fileType) + GlobalConfig.getSlash()+ eachFileName;
			   caseIDList = XmlExtents.getAttributeValueByElementName(dataFilePath, "TestCase", "ID");
		}
		return caseIDList;
	}
	
	/**
	 * ���ݴ����DataFile��������ȡexecutor������Ҫ����Ĳ���
	 * @param fileType
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static List<String> getExecutorParams(DataFileType fileType, String fileName, String sheetName) throws IOException, DocumentException{
		List<String> executorParamList = new ArrayList<String>();
				
		if(fileType == DataFileType.EXCEL)
		{
			List<String> ids = getTestMethodNames(fileType, fileName, sheetName);
			for(String id:ids)
			{
				executorParamList.add("\"" + fileName.substring(0, fileName.lastIndexOf(".")) + "." + sheetName + "." + id + "\"");
			}
		}
		else
		{
			List<String> testCaseNames = getTestClassNames(fileType, fileName);
			for(String eachTestCaseName:testCaseNames)
			{
				List<String> testCaseIds = getTestCaseID(fileType, fileName, "");
				for(String id:testCaseIds)
				{
					executorParamList.add("\"" + fileName + "\"" + ","+"\""+id+"\"");
				}
			}
		}
		return executorParamList;
	}
}
