//请不要修改本文件名
package serviceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.Arrays;

import service.ExecuteService;
import service.UserService;

public class ExecuteServiceImpl implements ExecuteService {

	/**
	 * 请实现该方法
	 */
	public String execute(String code, String param) {
	
		String str="";
		int strLength=code.length();
		char[] objects=new char[strLength];//对象
		char[] bfChars=code.toCharArray();//原字符
		Arrays.fill(objects, (char)0);//
		int nownum=0;//指针
		int paramNum=0;
		
		for (int i = 0; i < strLength; i++) {
			char c=bfChars[i];
			switch (c) {
			case'>':nownum++;break;
			case'<':nownum--;break;
			case'+':objects[nownum]=(char)((int)objects[nownum]+1);break;
			case'-':objects[nownum]=(char)((int)objects[nownum]-1);break;
			case'.':
				//System.out.print(objects[nownum]);
				str=str+objects[nownum];
				break;
			case',':objects[nownum]=(char)param.charAt(paramNum);
				i++;break;
			case'[':
				if(objects[nownum]==0){
					int a=0;
					loop:for (int j =i+1; j < strLength; j++) {
						if (bfChars[j]=='[') {
							a++;
						}
						if (bfChars[j]==']') {
							a--;
						}
						if (a<0) {
							i=j-1;
							break loop;
						}
					}
				}
				break;
			case']':
				if (objects[nownum]!=0) {
					int a=0;
					loop:for (int j =i-1; j>=0; j--) {
						if (bfChars[j]==']') {
							a++;
						}
						if (bfChars[j]=='[') {
							a--;
						}
						if (a<0) {
							i=j-1;
							break loop;
						}
					}
				}
				break;
			default:
				System.err.println("出现未识别的字符"+c);
			}
		}
		return str;
	}

}
