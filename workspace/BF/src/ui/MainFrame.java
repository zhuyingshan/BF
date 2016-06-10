package ui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;



public class MainFrame extends JFrame {
 
	public JTextArea textArea,inputArea,outputArea;
	private JLabel outputLable,inputLable,bottomLable;
	String oldValue;//存放编辑区原来的内容，用于比较文本是否有改动
	boolean isNewFile=true;//是否新文件(未保存过的)
	File currentFile;//当前文件名
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
	    int frameHeight=0,frameWidth=0;//窗口的高度和宽度
		if (screenWidth*3>screenHeight*4) {
			frameHeight=screenHeight/2;
			frameWidth=screenHeight*2/3;
		}else{
			frameWidth=screenHeight/2;
			frameHeight=screenHeight*3/8;
		}
        this.setBounds((screenWidth-frameWidth)/2, (screenHeight-frameHeight)/2, frameWidth, frameHeight);
		
        //菜单栏
		JMenuBar menuBar = new JMenuBar();
		
		//文件
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
		
		JMenu versionMenu= new JMenu("Version");
		versionMenu.setFont(new java.awt.Font("Dialog", 1, 18));
	    JMenuItem[] verItems=new JMenuItem[5];
	    for (int i = 0; i < 5; i++) {
			verItems[i]=new JMenuItem("2016"+i);
			verItems[i].setFont(new java.awt.Font("Dialog",1,18));
			versionMenu.add(verItems[i]);
		}
		
		menuBar.add(fileMenu);
		menuBar.add(runMenu);
		menuBar.add(versionMenu);
		menuBar.setBounds(0, 0, frameWidth, frameHeight/12);
		this.add(menuBar);

		//代码区	
		textArea = new JTextArea();
		textArea.setBounds(0,frameHeight/12,frameWidth,frameHeight*6/12);
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setFont(new java.awt.Font("Dialog", 1, 15));
		this.add(textArea);
		JScrollPane jScrollPane=new JScrollPane();//滚条此处还需修改

		

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
		versionMenu.addActionListener(new ItemActionListener());
		versionMenu.setActionCommand("Version");
		
		//添加部件
		this.add(bottomLable);
		this.add(inputLable);
		this.add(outputLable);
		this.add(inputArea);
		this.add(outputArea);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	     /**监听器*/
	class ItemActionListener implements ActionListener {
		/**
		 * 子菜单响应事件
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if (cmd.equals("Open")) {
				try {
					openItem();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (cmd.equals("Save")) {
				try {
					saveItem();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (cmd.equals("New")) {
				newItem();
			}else if (cmd.equals("Exit")) {
				exitItem();
			}else if (cmd.equals("Version")) {
				versionItem();
			}else if (cmd.equals("Execute")) {
				executeItem();
			}
		}
		
		
		
        private void versionItem() {
			
		}
		private void newItem() {
			textArea.setText("");
			bottomLable.setText("已新建文件");
		}
		private void executeItem() {
			
		}
		private void saveItem() throws IOException {//保存方法
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
		private void openItem () throws IOException {
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
	
}
