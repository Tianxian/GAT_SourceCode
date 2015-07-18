package cn.gateside.gattmg.generation;

import cn.gateside.gattmg.infos.DataFileType;
import cn.gateside.gattmg.infos.ProjectInfos;
import cn.gateside.gattmg.util.ProjectUtil;

import com.gateside.autotesting.Lib.common.SimpleLogger;

public class FrameGenerator {

//	public static void main(String[] args) 
//	{
//		String[] args1={"0"};
//		if(args1[0].equals("1"))
//		{
//			SimpleLogger.logInfo("fds"+args[0]);
//			createWebUITestProject();
//		}
//		else
//		{
//		   createInterfaceTestProject();	
//		}
//	}
	
	public static void createInterfaceTestProject()
	{
		//����GatProjectTestng����
				ProjectGenerator.createProject();
				String testClassFilePath = ProjectUtil.getProjectPath() + ProjectInfos.SRC_PATH;
			
				try 
				{
					//����GatProject��Ŀ¼����ļ�(.classpath,.project,.settings�ļ�)
//					SimpleLogger.logInfo("Start create project files");
//					ProjectGenerator.createProjectFile(ProjectUtil.getProjectPath());
					//LibsGenerator.copyLibFiles();
					//����GatProject Src��TestClass�ļ�
					
					SimpleLogger.logInfo("Start create interface Excel Testcase class files");
					TestClassGenerator.generateTestClassFile(DataFileType.EXCEL,
							testClassFilePath);
					
					SimpleLogger.logInfo("Start create Interface XML Testcase class files");
					TestClassGenerator.generateTestClassFile(DataFileType.XML,
							testClassFilePath);
					//����testng.xml�ļ�
					
					SimpleLogger.logInfo("Start create testng.xml files");
					ProjectUtil.createTestngXml();
				} catch (Exception e)
				{
					SimpleLogger.logError(e);
				}	
	}
	
	public static void createWebUITestProject()
	{
		//����GatProjectTestng����
				ProjectGenerator.createProject();
				String testClassFilePath = ProjectUtil.getProjectPath() + ProjectInfos.SRC_PATH;
			
				try 
				{
					//����GatProject��Ŀ¼����ļ�(.classpath,.project,.settings�ļ�)
//					SimpleLogger.logInfo("Start create project files");
//					ProjectGenerator
//							.createProjectFile(ProjectUtil.getProjectPath());
					//LibsGenerator.copyLibFiles();
					//����GatProject Src��TestClass�ļ�
					
					SimpleLogger.logInfo("Start create Webui Testcase class files");
					TestClassGenerator.generateTestClassFile(DataFileType.WebUIXML,
							testClassFilePath);
					//����testng.xml�ļ�
					
					SimpleLogger.logInfo("Start create testng.xml files");
					ProjectUtil.createTestngXml();
				} catch (Exception e)
				{
					SimpleLogger.logError(e);
				}	
	}
	
	}
