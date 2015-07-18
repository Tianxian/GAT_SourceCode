package cn.gateside.gattmg.generation;

import java.io.IOException;

import com.gateside.autotesting.Lib.common.ConfigReader;

import cn.gateside.gattmg.infos.TempType;
import cn.gateside.gattmg.util.ProjectUtil;
import cn.gateside.gattmg.util.TemplateUtil;


public class ProjectGenerator {
	
	/**
	 * 生成工程文件
	 */
	public static void createProject(){
		//生成TestngProject
		new ProjectUtil().createProjectDir(ProjectUtil.getProjectBasePath());
	}
	
	/**
	 * 生成工程里的文件并写入内容
	 * @param filePath
	 * @param fileContents
	 * @throws IOException 
	 */
	public static void createProjectFile(String filePath) throws IOException{
		//获取tmpFile文件路径
		String tmpString = TemplateUtil.getTempPath();
		//获取需要生成的tmpFile文件名
//		List<String> tmpNames = TemplateUtil.tempFileNameList();
		
		//读取tmpFile文件并生成相应文件内容
		TempType[] tempNames = TempType.values();
		for(TempType eachTempName : tempNames)
		{
			String tmpFileContents = TemplateUtil.getTempFileString(tmpString, eachTempName.name());
			tmpFileContents=formatprojectFileContent(tmpFileContents,eachTempName);
			ProjectUtil.createProjectFiles(filePath, eachTempName, tmpFileContents);
		}
	}
	
	private static String formatprojectFileContent(String tempeteContent,TempType temName)
	{
		String result="";

		result=tempeteContent.replace("{PROJECTNAME}",ConfigReader.GetValue("gatCreator.properties","projectName"));

		result=result.replace("{LIBPATH}",ConfigReader.GetValue("gatCreator.properties","libPath").replaceAll("\\\\","\\\\\\\\"));

		result=result.replace("{ROOTDIR}",ConfigReader.GetValue("gatCreator.properties","rootDir").replaceAll("\\\\","\\\\\\\\"));
		
		//result=result.replaceAll("\\\\","\\\\\\\\");
		return result; 
	}
	
}
