package serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import service.IOService;

public class IOServiceImpl implements IOService{
	/**保存设置，保存成功返回true*/	
	@Override
	public boolean writeFile(String file, String userId, String fileName) {
		String path="D://Java程序//workspace_v1.0//workspace//BF程序"+"//"+userId+"//"+fileName;
	
		for (int i = 0; i < 4; i++) {
			String oldPath=path+"//"+(char)(i+'a')+".txt";
			String newPath=path+"//"+(char)(i+'a'+1)+".txt";
			File oldFile=new File(oldPath);
			File newFile=new File(newPath);
			try {
				Files.copy(oldFile.toPath()	,newFile.toPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			path=path+"//a.txt";
			FileWriter fw = new FileWriter(path, false);
			fw.write(file);
			fw.flush();
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String readFile(String userId, String fileName) {//filename 的格式是zys//a.txt;
		String path="D://Java程序//workspace_v1.0//workspace//BF程序"+"//"+userId+"//"+fileName;
		String str="";
		try {
			BufferedReader bReader=new BufferedReader(new FileReader(path));
			try {
				str=str+bReader.readLine()+"\n";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				bReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("未找到文件");
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public String readFileList(String userId) {
		String path="D://Java程序//workspace_v1.0//workspace//BF程序//"+userId;
		System.out.println(userId);
		File file = new File(path);     // get the folder list  
		String str="";
		File[] array = file.listFiles();   
	        for(int i=0;i<array.length;i++){   
	                str=str+(array[i].getName()+" ");   // take file name 
	        }   
		return str.trim();
	}
	
}
