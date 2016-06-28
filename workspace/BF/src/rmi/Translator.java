package rmi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Translator {

	public static void translate(String bfString) throws IOException {
		int strLength=bfString.length();
		char[] objects=new char[strLength];//对象
		char[] bfChars=bfString.toCharArray();//原字符
		Arrays.fill(objects, (char)0);//
		int nownum=0;//指针
		BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
		for (int i = 0; i < strLength; i++) {
			char c=bfChars[i];
			switch (c) {
			case'>':nownum++;break;
			case'<':nownum--;break;
			case'+':objects[nownum]=(char)((int)objects[nownum]+1);break;
			case'-':objects[nownum]=(char)((int)objects[nownum]-1);break;
			case'.':
				System.out.print(objects[nownum]);
				break;
			case',':objects[nownum]=(char)bf.read();break;
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
				System.err.println("出现未识别的字符");
			}
		}
		bf.close();
	}
}
