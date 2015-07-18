package cn.gateside.gattmg.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;






import cn.gateside.gattmg.extents.XmlExtents;
import cn.gateside.gattmg.infos.ProjectInfos;
import cn.gateside.gattmg.infos.TempType;

import com.gateside.autotesting.Gat.util.GlobalConfig;

public class ProjectUtil {
	
	/**
	 * 获取生成project的根路径
	 * @param file
	 * @return
	 */
	public static String getProjectBasePath(){
		File parentFile = null;
		String basePath = null;
		try {
			parentFile = FileUtil.pathToFile("..");
			basePath = FileUtil.getFileDir(parentFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return basePath;
	}
	
	/**
	 * 生成的全部文件路径存放在ArrayList中
	 * @return
	 */
	private ArrayList<String> appendDirs(){
		ArrayList<String> wholeDirStrings = new ArrayList<String>();
//		wholeDirStrings.add(ProjectInfos.PRO_BIN_DIR);
		wholeDirStrings.add(ProjectInfos.PRO_SRC_DIR);
//		wholeDirStrings.add(ProjectInfos.PRO_LIB_DIR);
//		wholeDirStrings.add(ProjectInfos.PRO_TEST_OUTPUT_DIR);
//		wholeDirStrings.add(ProjectInfos.PRO_SETTINGS_DIR);
		return wholeDirStrings;
	}
	
	/**
	 * 根据要求生成Project下所有文件夹
	 * @param projectBasePath
	 * @return
	 */
	public void createProjectDir(String proBasePath){
		if(proBasePath.contains("\\")){
			proBasePath = proBasePath.replace("\\", "/");
		}
		for(int i=0; i<this.appendDirs().size(); i++){
			FileUtil.createFileDir(proBasePath + this.appendDirs().get(i).replace("\\", "/"));
		}
	}
	
	/**
	 * 生成Project根目录下的各种文件
	 * @param fileName
	 */
	public static void createProjectFiles(String proBasePath, TempType tmpTypeName, String fileContents){
		String fileName = null;
		
		switch(tmpTypeName){
//		case ClasspathTmp:
//			proBasePath = ProjectUtil.getProjectPath() + GlobalConfig.getSlash();
//			fileName = ProjectInfos.PRO_CLASSPATH_FILE;
//			FileUtil.createFile(proBasePath, fileName, fileContents, false);
//			break;
//		case ProjectTmp:
//			proBasePath = ProjectUtil.getProjectPath() + GlobalConfig.getSlash();
//			fileName = ProjectInfos.PRO_FILE;
//			FileUtil.createFile(proBasePath, fileName, fileContents, false);
//			break;
//		case SettingsTmp:
//			proBasePath = getProjectPath() + GlobalConfig.getSlash()+".settings"+GlobalConfig.getSlash();
//			fileName = ProjectInfos.PRO_SETTINGS_FILE;
//			FileUtil.createFile(proBasePath, fileName, fileContents, false);
//			break;
//		case GatConfigTmp:
//			proBasePath = ProjectUtil.getProjectPath() + GlobalConfig.getSlash();
//			fileName = ProjectInfos.PRO_GAT_CONFIG;
//			FileUtil.createFile(proBasePath, fileName, fileContents, false);
//			break;
//		case LogConfigTmp:
//			proBasePath = ProjectUtil.getProjectPath() + GlobalConfig.getSlash();
//			fileName = ProjectInfos.PRO_LOG_CONFIG;
//			FileUtil.createFile(proBasePath, fileName, fileContents, false);
//			break;
//		case buildTmp:
//			proBasePath = ProjectUtil.getProjectPath() + GlobalConfig.getSlash();
//			fileName = ProjectInfos.PRO_BUILD_FILE;
//			FileUtil.createFile(proBasePath, fileName, fileContents, false);
//			break;
		case TestClassTmp:
			proBasePath = getProjectPath() + ProjectInfos.SRC_PATH;
			break;
		default:
			
		}
	}	
	
	/**
	 * 获取当前工程路径
	 * @return
	 */
	public static String getProjectPath(){
		String projectPath = ProjectUtil.getProjectBasePath()+GlobalConfig.getSlash()+ProjectInfos.getProjectName();
		return projectPath;
	}
	
	/**
	 * 生成工程的Testng.xml文件
	 * @throws IOException
	 */
	public static void createTestngXml() throws IOException
	{
		List<String> classStringNames = new ArrayList<String>();
		List<String> classNames = FileUtil.getFilesNameNoSuffix(ProjectUtil.getProjectPath() + ProjectInfos.SRC_PATH);
		
		for(String eachName:classNames)
		{
			classStringNames.add("com.gateside.autotesting.generation.unittest." + eachName);
		}
		Document document = XmlExtents.createXml("suite", classStringNames);
		XmlExtents.XmlOutput(document, ProjectUtil.getProjectPath() + GlobalConfig.getSlash(), "testng.xml");
	}
}
