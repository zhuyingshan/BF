package ui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Position.Bias;

import com.sun.glass.ui.MenuItem;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import javafx.scene.control.TextArea;
import rmi.RemoteHelper;
import rmi.Translator;
import service.ExecuteService;
import service.IOService;
import service.UserService;
import sun.util.logging.resources.logging;


//测试:++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.
public class MainFrame extends JFrame  {
 
	
	int state=0;//0代表未登录，1代表登录
	JPanel p1,p2,p3;
	JButton b1;  
	JTextField tField;
	JPasswordField pField;
	JFrame frame;JLabel l1,l2,l3;
	JMenuBar menuBar ;
	JMenu saveMenu,openMenu,versionMenu;
	
	
	public JTextArea textArea,inputArea,outputArea;
	private JLabel outputLable,inputLable,bottomLable;
	
	String useID,fileName;
    String[] version=new String[5];//用于版本
	
	
	public MainFrame() {
		// 创建窗体
		this.setTitle("BF Client");
		this.setFont(new java.awt.Font("Dialog",1,18));
		this.setLayout(null);
		
		//使得窗口更合适
		Toolkit toolkit=Toolkit.getDefaultToolkit();
		Dimension screenSize=toolkit.getScreenSize();
		int screenHeight=screenSize.height;
		int screenWidth=screenSize.width;
	    int frameHeight=540,frameWidth=720;//窗口的高度和宽度
        this.setBounds((screenWidth-frameWidth)/2, (screenHeight-frameHeight)/2,frameWidth+10,frameHeight);
		
        
        //菜单栏
		 menuBar = new JMenuBar();
		
		//文件
		
		 
		JMenu accountMenu=new JMenu("Account");
		accountMenu.setFont(new java.awt.Font("Dialog", 1, 18));
		JMenuItem loginInItem=new JMenuItem("Login In");
		loginInItem.setFont(new java.awt.Font("Dialog", 1, 18));
		JMenuItem loginOutItem=new JMenuItem("Login Out");
		loginOutItem.setFont(new java.awt.Font("Dialog", 1, 18));
		accountMenu.add(loginInItem);
		accountMenu.add(loginOutItem);
		JMenu fileMenu = new JMenu("File");
		fileMenu.setFont(new java.awt.Font("Dialog", 1, 18));
		JMenuItem newMenuItem = new JMenuItem("New");
		newMenuItem.setFont(new java.awt.Font("Dialog", 1, 18));
		fileMenu.add(newMenuItem);
		JMenuItem openMenuItem = new JMenuItem("Open");
		openMenuItem.setFont(new java.awt.Font("Dialog", 1, 18));
		fileMenu.add(openMenuItem);
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setFont(new java.awt.Font("Dialog", 1, 18));
		fileMenu.add(saveMenuItem);
		JMenuItem exitMenuItem=new JMenuItem("Exit");
		exitMenuItem.setFont(new java.awt.Font("Dialog", 1, 18));
		fileMenu.add(exitMenuItem);
		
		//其他的菜单栏
		JMenu runMenu=new JMenu("Run");
		runMenu.setFont(new java.awt.Font("Dialog", 1, 18));
		JMenuItem excuteItem=new JMenuItem("Execute");
		excuteItem.setFont(new java.awt.Font("Dialog", 1, 18));
		runMenu.add(excuteItem);
		
		
	
		menuBar.add(fileMenu);
		menuBar.add(runMenu);
		
		menuBar.add(accountMenu);
		menuBar.setBounds(0, 0, frameWidth, frameHeight/12);
		this.add(menuBar);

		//代码区	
		JPanel textPanel=new JPanel();
		textPanel.setBounds(0,frameHeight/12,frameWidth,frameHeight*6/12);
		
		textArea = new JTextArea(12,58);
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setFont(new java.awt.Font("Dialog", 1, 15));
		JScrollPane jScrollPane=new JScrollPane(textArea);//滚条此处还需修改,无效
		jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		textPanel.add(jScrollPane);
		this.add(textPanel);
		
		// 显示标签
		outputLable = new JLabel("Output:");
		outputLable.setBounds(frameWidth/2, frameHeight*7/12, frameWidth/2, frameHeight/12);
		outputLable.setFont(new java.awt.Font("Dialog", 1, 18));
		inputLable=new JLabel("Input:");
		inputLable.setBounds(0, frameHeight*7/12, frameWidth/2, frameHeight/12);
		inputLable.setFont(new java.awt.Font("Dialog", 1, 18));
		bottomLable=new JLabel("By Zys");
		bottomLable.setBounds(0, frameHeight*41/48, frameWidth, frameHeight/12);
	
		
		
		/*输入数据和输出数据区域*/
		inputArea=new JTextArea();
		inputArea.setBounds(0, frameHeight*2/3, frameWidth*5/11, frameHeight/5);
		inputArea.setBackground(Color.LIGHT_GRAY);
		inputArea.setFont(new java.awt.Font("Dialog", 1, 15));
		outputArea=new JTextArea();
		outputArea.setBounds(frameWidth/2, frameHeight*2/3, frameWidth/2, frameHeight/5);
		outputArea.setBackground(Color.LIGHT_GRAY);
		outputArea.setFont(new java.awt.Font("Dialog", 1, 15));
		
		
		//监听器
		newMenuItem.addActionListener(new ItemActionListener());
		newMenuItem.setActionCommand("New");
		openMenuItem.addActionListener(new ItemActionListener());
		openMenuItem.setActionCommand("Open");
		exitMenuItem.addActionListener(new ItemActionListener());
		exitMenuItem.setActionCommand("Exit");
		saveMenuItem.addActionListener(new ItemActionListener());
		saveMenuItem.setActionCommand("Save");
		excuteItem.addActionListener(new ItemActionListener());
		excuteItem.setActionCommand("Execute");
		
		loginInItem.addActionListener(new ItemActionListener());
		loginInItem.setActionCommand("LoginIn");
		loginOutItem.addActionListener(new ItemActionListener());
		loginOutItem.setActionCommand("LoginOut");
		//添加部件
		this.add(bottomLable);
		this.add(inputLable);
		this.add(outputLable);
		this.add(inputArea);
		this.add(outputArea);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

 public class LoginFrame{
    	 
		   
		public void main(String[]args) {
			LoginFrame loginFrame=new LoginFrame();
			
		}

		private LoginFrame() {
	      frame=new JFrame();
		  frame.setLayout(new GridLayout(3,1));
		  p1=new JPanel();
		  p2=new JPanel();
		  p3=new JPanel();
		  l1=new JLabel("用户名");
		  l2=new JLabel("密    码");
		  b1=new JButton("登录");
		  l3=new JLabel("请输入用户名和密码");
		  tField=new JTextField(10);
		  pField=new JPasswordField(10);
		    
		   
		  p1.add(l1);p1.add(tField);
		  p2.add(l2);p2.add(pField);
		  p3.add(l3);
		  p3.add(b1);
		  frame.add(p1);
		  frame.add(p2);
		  frame.add(p3);
		  
		  frame.setBounds(800,400,300,200);
		  frame.setVisible(true);
		  frame.setResizable(false);
		
		  b1.setActionCommand("登录");
		  b1.addActionListener(new ItemActionListener());
		}		
		}



	/**监听器*/
	class ItemActionListener implements ActionListener {
		/**
		 * 子菜单响应事件
		 */
	
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if (cmd.contains("Version")) {
				int i=cmd.charAt(8)-'0';
				try {
					versionItem(i);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return ;
			}else if (cmd.contains("File") ){
				try {
					fileName=cmd.substring(4);
					if (versionMenu!=null) {
						menuBar.remove(versionMenu);
					}
				
					System.out.println(cmd.substring(4)+"//a.txt");
					readItem(cmd.substring(4)+"//a.txt");
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else{
			switch (cmd) {
			case "Open":
				try {
					openItem();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
	
			case "Save":
				try {
					saveItem();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "New":
				newItem();
				break;
			case "Exit":
				exitItem();
				break;
			
			case "Execute":
				try {
					executeItem();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "LoginIn":
				loginInItem();
				break;
			case "LoginOut":
				loginOutItem();
				break;
			case"登录":
				try {
					loginItem();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			default:
				System.err.println("有未知的指令"+cmd);
				break;
			}
		}
	}
		
		
	
	
		private void readItem(String file) throws RemoteException {//读取文件，file=zys1//a.txt
			String text=RemoteHelper.getInstance().getIOService().readFile(useID,file);
			bottomLable.setText("已读取文件："+fileName);
			textArea.setText(text);
			//version事件
		    versionMenu= new JMenu("Version");
			versionMenu.setFont(new java.awt.Font("Dialog", 1, 18));
		    JMenuItem[] verItems=new JMenuItem[5];
		    for (int i = 0; i < 5; i++) {
			verItems[i]=new JMenuItem(fileName+"--"+i);
			verItems[i].setFont(new java.awt.Font("Dialog",1,18));
			versionMenu.add(verItems[i]);
			verItems[i].addActionListener(new ItemActionListener());
		    verItems[i].setActionCommand("Version"+"["+i+"]");
		}
		    menuBar.add(versionMenu);
			
		}

		private void versionItem(int i) throws IOException {//某一个文件的历史格式
			System.err.println(fileName);
			String file=fileName+"\\"+(char)('a'+i)+".txt";
			String text=RemoteHelper.getInstance().getIOService().readFile(useID,file);
			bottomLable.setText("已读取版本："+fileName+" Version--"+i);
			textArea.setText(text);
			}
		}




		private void loginItem() throws RemoteException {
			if (RemoteHelper.getInstance().getUserService().login(tField.getText(), pField.getText())) {
				useID=tField.getText();
				System.err.println(useID);
				bottomLable.setText("登录成功,当前用户："+useID);
				state=1;
			    frame.dispose();
			
			  
			    //新建菜单栏项目
			    saveMenu= new JMenu("SaveToService");
				saveMenu.setFont(new java.awt.Font("Dialog", 1, 18));
				menuBar.add(saveMenu);
				JMenuItem saveTSItem=new JMenuItem("Save");
				saveTSItem.setFont(new java.awt.Font("Dialog", 1, 18));
				saveTSItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String text=textArea.getText();
						try {
							RemoteHelper.getInstance().getIOService().writeFile(text, useID, fileName);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				});
				saveMenu.add(saveTSItem);
				
				//保存的措施
				
				//新建菜单栏项目
				openMenu = new JMenu("Open");
System.out.println(386);			
				openMenu.setFont(new java.awt.Font("Dialog", 1, 18));
				String fileList=RemoteHelper.getInstance().getIOService().readFileList(useID);
				String[] files=fileList.split(" ");
				
			
				JMenuItem[] openItem=new JMenuItem[files.length];//文件有哪些
				for (int i = 0; i < files.length; i++) {
					openItem[i]=new JMenuItem(files[i]);//传入的file有包括版本
					openItem[i].setFont(new java.awt.Font("Dialog",1,18));
					openMenu.add(openItem[i]);
					openItem[i].addActionListener(new ItemActionListener());
				    openItem[i].setActionCommand("File"+files[i]);
				}
				
			    menuBar.add(openMenu);
			}else {
					l3.setText("用户名或密码错误");
			}
		}




		private void loginOutItem() {
	    	 bottomLable.setText("未登录状态");
	    	 state=0;
	    
	    	 menuBar.remove(saveMenu);
	    	 menuBar.remove(versionMenu);
	    	 menuBar.remove(openMenu);
		}
	
	
	
		private void loginInItem() {
			LoginFrame loginFrame=new LoginFrame();
			
		}
		
		private void newItem() {
			
			JFrame newFrame =new JFrame("新建文件");
			  
			  
			textArea.setText("");
			
			/*b11.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String name=tfField.getText();
					String path="D://Java程序//workspace_v1.0//workspace//BF程序";
					File file =new File(path+"//"+name);    
					if  (!file .exists()  && !file .isDirectory())  	//如果文件夹不存在则创建        
					{        
					    file .mkdir();  
					    for (int i = 0; i < 5; i++) {
							File version=new File(path+"//"+i);
							try {
								version.createNewFile();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					    
					} else   
					{  
					   label.setText("已存在请重新输入："); 
					}  
				}
			});
			newFrame.setBounds(800,400,400,300);
			newFrame.add(p1);
			newFrame.add(p2);
			newFrame.setVisible(true);
			newFrame.setResizable(false);*/
			bottomLable.setText("已新建文件");
		}
		
		
		private void executeItem() throws IOException {
			String codeStr=textArea.getText();
			String inputStr=inputArea.getText();
			/*ByteArrayOutputStream bStream=new ByteArrayOutputStream(1024);//创建一个打印流准备接受输出数据。
			PrintStream cachestream=new PrintStream(bStream);//创建bstream的打印流
			PrintStream pStream=System.out;
			System.setOut(cachestream);//若去掉此句，则内容都会在console面板中输出，改变流
			RemoteHelper.getInstance().getExecuteService().execute(textArea.getText(), inputStr);
			String output=bStream.toString();
			System.setOut(pStream);//释放该在控制台输出的流*/		
			System.out.println(codeStr);
			System.out.println(inputStr);
			String output=RemoteHelper.getInstance().getExecuteService().execute(textArea.getText(), inputStr);
			outputArea.setText(output);
		}
		
		
		private void saveItem() throws IOException {//保存文件版本
			JFileChooser fileChooser=new JFileChooser("");
			fileChooser.showOpenDialog(null);
			File file=fileChooser.getSelectedFile();
			FileWriter out=new FileWriter(file);
			char[]buffer=textArea.getText().toCharArray();
			out.write(buffer);
			out.close();
			bottomLable.setText("文件已存储至:"+file.getPath());
		}
		
		
		private void exitItem() {
			System.exit(0);
		}
		
		
		private void openItem () throws IOException {//打开服务器的文件
			JFileChooser fileChooser=new JFileChooser("");
			fileChooser.showOpenDialog(null);
			File file=fileChooser.getSelectedFile();
			BufferedReader bReader=new BufferedReader(new FileReader(file));
			String s,text="";
			while((s=bReader.readLine())!=null){
			text=text+"\n"+s;
			textArea.setText(text);
			}
			bottomLable.setText("正在打开文件:"+file.getName());
			bReader.close();
		}
		
	}



	

