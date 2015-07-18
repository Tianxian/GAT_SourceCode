package com.baidu.gameqa.unittest;

import java.io.IOException;

import org.dom4j.DocumentException;

import cn.gateside.gattmg.generation.FrameGenerator;
import cn.gateside.gattmg.generation.ProjectGenerator;
import cn.gateside.gattmg.generation.TestClassGenerator;
import cn.gateside.gattmg.infos.DataFileType;
import cn.gateside.gattmg.infos.ProjectInfos;
import cn.gateside.gattmg.util.ProjectUtil;

public class ProjectUnitTest {

	public static void main(String[] args) throws Exception {
		//生成GatProjectTestng工程
//		ProjectGenerator.createProject();
//		//生成GatProject根目录相关文件(.classpath,.project,.settings文件)
//		ProjectGenerator.createProjectFile(ProjectUtil.getProjectPath());
//		System.out.println(ProjectUtil.getProjectPath());
//		//生成GatProject Src的TestClass文件
//		String testClassFilePath = ProjectUtil.getProjectPath() + ProjectInfos.SRC_PATH;
//		TestClassGenerator.generateTestClassFile(DataFileType.EXCEL, testClassFilePath);
//		TestClassGenerator.generateTestClassFile(DataFileType.XML, testClassFilePath);
//		ProjectUtil.createTestngXml();
		FrameGenerator generator=new FrameGenerator();
		String[] args1={"0"};
//		generator.main(args1);
	}
}
