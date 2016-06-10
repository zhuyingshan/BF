package runner;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import rmi.RemoteHelper;
import service.IOService;
import ui.MainFrame;
/**客户端的启动项
BY 朱应山*/


public class ClientRunner {
	private RemoteHelper remoteHelper;
	
	/**构造方法，包括连接服务器和启动用户图形界面*/
	public ClientRunner() {
		linkToServer();
		initGUI();
	}
	/**连接服务器*/
	private void linkToServer() {
		try {
			remoteHelper = RemoteHelper.getInstance();
			remoteHelper.setRemote(Naming.lookup("rmi://localhost:8888/DataRemoteObject"));
			System.out.println("linked");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	/**启动界面*/
	private void initGUI() {
		MainFrame mainFrame = new MainFrame();
		
	}
	
	public void test(){
		try {
			System.out.println(remoteHelper.getUserService().login("admin", "123456a"));
			System.out.println(remoteHelper.getIOService().writeFile("2", "admin", "testFile"));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		ClientRunner cr = new ClientRunner();
		//cr.test();
	}
}
