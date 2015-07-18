package cn.gateside.gattmg.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 公用util--文件操作类
 * @author yanglei12
 *
 */
public class FileUtil {
	
	private static boolean flag = false;
	private static BufferedInputStream inputStream = null;
	private static BufferedOutputStream outputStream = null;
	private BufferedReader bufferedReader = null;
	private BufferedWriter bufferedWriter = null;
	
	/**
	 * 通过文件路径生成获取对应File
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static File pathToFile(String filePath) throws IOException{
		File file = new File(filePath);
		return file;
	}
	
	/**
	 * 判断method传递参数是否为null
	 * @param params
	 * @return
	 */
	public boolean isParamsNull(String... params){
		if(params == null){
			return true;
		}else
			return false;
	}
	
	/**
	 * 根据给定文件获取标准文件路径
	 * @param file
	 * @return
	 */
	public static String getFileDir(File file) throws IOException{
		String filePath = file.getCanonicalPath();
		return filePath;
	}
	
	/**
	 * 获取项目工程的路径
	 * @return
	 * @throws IOException
	 */
	public static String getProjectDir() throws IOException {
		File directory = new File("");
		String proDir = directory.getCanonicalPath();
		return proDir;
	}
	
	/**
	 * 根据给定文件路径，生成文件夹
	 * @param filePath
	 * @return
	 */
	public static File createFileDir(String filePath){
		File file = new File(filePath);
		file.mkdirs();
		return file;
	}
	
	/**
	 * 根据文件路径，文件名称，文件内容创建相应文件
	 * @param filePath
	 * @param fileName
	 * @param fileContent
	 * @return
	 */
	public static boolean createFile(String filePath, String fileName, String fileContent, boolean flag){
		FileUtil fileUtil = new FileUtil();
		String pathString = filePath + fileName; 
		
//		System.out.println("=======================================");
		if(!fileUtil.isParamsNull(filePath, fileName))
		{
			try {
				System.out.println(filePath);
				System.out.println(fileName);
				File file = FileUtil.pathToFile(pathString.toString());
				flag = file.createNewFile();
				FileWriter fileWriter = new FileWriter(file, flag);
				PrintWriter printWriter = new PrintWriter(fileWriter);
				printWriter.println(fileContent.toString());
				printWriter.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
	 * 将指定文件转化为String类型方便写入
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public static String fileToString(BufferedInputStream inputStream) throws IOException{
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));
		
		for(String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()){
			stringBuilder.append(line + "\n");
		}
		bufferedReader.close();
		return stringBuilder.toString();
	}
	
	/**
	 * 将字符串写入到文件中
	 * @param resString
	 * @param desFile
	 * @return
	 */
	public boolean stringToFile(String resString, File desFile) {
		char buffer[] = new char[1024];
		int len = 0;
		try {
			bufferedReader = new BufferedReader(new StringReader(resString));
			bufferedWriter = new BufferedWriter(new FileWriter(desFile, true));
			while((len = bufferedReader.read(buffer)) != -1){
				bufferedWriter.write(buffer, 0, len);
			}
			bufferedWriter.flush();
			bufferedReader.close();
			bufferedWriter.close();
			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
			return flag;
		}
		return flag;
	}
	
	/**
	 * 获取指定路径下所有文件名，返回String类型的List对象
	 * @param filePath
	 * @return
	 */
	public static List<String> getFilesName(String filePath){
		List<String> xmlFileNames = new ArrayList<String>();
		FileUtil fileUtil = new FileUtil();
		
		if(!fileUtil.isParamsNull(filePath)){
			try {
				File file = FileUtil.pathToFile(filePath);
				if (file.isDirectory()) {
					File[] files = file.listFiles();
					if (files.length == 0) {
						return xmlFileNames;
					}
					for (int i = 0; i < files.length; i++) {
						xmlFileNames.add(i, files[i].getName());
					}
					return xmlFileNames;
				} else if (file.isFile()) {
					xmlFileNames.add(file.getName());
					return xmlFileNames;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return xmlFileNames;
	}
	
	/**
	 * 获取指定路径下所有文件名，返回String类型的List对象(不包含文件名后缀)
	 * @param filePath
	 * @return
	 */
	public static List<String> getFilesNameNoSuffix(String filePath){
		List<String> javaFileNames = new ArrayList<String>();
		FileUtil fileUtil = new FileUtil();
		String fileName="";
		if(!fileUtil.isParamsNull(filePath)){
			try {
				File file = FileUtil.pathToFile(filePath);
				if (file.isDirectory()) {
					File[] files = file.listFiles();
					for (int i = 0; i < files.length; i++)
					{
					    fileName=files[i].getName();
					    javaFileNames.add(i, files[i].getName().substring(0, files[i].getName().lastIndexOf(".")));
					}
				} else if (file.isFile()) 
				{
					fileName=file.getName();
					javaFileNames.add(file.getName().substring(0, file.getName().lastIndexOf(".")));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(fileName);
				e.printStackTrace();
			}
		}
		return javaFileNames;
	}
	
	/**
	 * 复制具体文件从一个路径到另一个路径
	 * @param oldFilePath
	 * @param newFilePath
	 * @param fileName
	 * @return
	 */
	public static boolean copyFiles(String oldFilePath, String newFilePath, String fileName) throws IOException{		
		FileUtil fileUtil = new FileUtil();
		
		if(!fileUtil.isParamsNull(oldFilePath, newFilePath, fileName)){
			try {
				File oldFile = FileUtil.pathToFile(oldFilePath + fileName);
				File newFile = FileUtil.pathToFile(newFilePath);
				if(oldFile.exists() && newFile.exists()){
					inputStream = new BufferedInputStream(new FileInputStream(oldFilePath + fileName));
					outputStream = new BufferedOutputStream(new FileOutputStream(newFilePath + fileName));
					byte[] buffer = new byte[102400*1000];
					int len = inputStream.read(buffer);
					while(len != -1){
						outputStream.write(buffer, 0, len);
						len = inputStream.read(buffer, 0, len);
					}
					outputStream.flush();
				}else{
					return flag;
				}
			} finally {
				if(inputStream != null){
					inputStream.close();
				}
				if(outputStream != null){
					outputStream.close();
				}
			}
		}
		return flag;
	}
}
