package serviceImpl;

import java.rmi.RemoteException;

import service.UserService;

public class UserServiceImpl implements UserService{

	@Override
	public boolean login(String username, String password) throws RemoteException {
		if (username.equals("zys")&&password.equals("000000")) {
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		return true;
	}

}
