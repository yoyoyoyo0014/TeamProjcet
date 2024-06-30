package TeamProject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class StreamThread<T>  {
	protected void OutStreamThread(T obj, Socket socket){
		Thread t = new Thread(()->{try {
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}});
		t.start();
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> InputStreamThread(Socket socket,List<T> ipsList){
		ipsList.add(null);
		Thread t = new Thread(()->{try {
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				try {
					ipsList.set(ipsList.size()-1, (T)ois.readObject());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
		} catch (IOException e) {
			e.printStackTrace();
		}});
		t.start();
		System.out.println("받는 정보 량 : "+ (ipsList.size()));
		return ipsList;
	}
	
}
