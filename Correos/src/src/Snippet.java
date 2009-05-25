package src;

import com.umbrella.mail.mailbox.KeepAliveThread;

public class Snippet {
	public static void main(String[] args) {
		//Creacion del hilo de keepAlive
				_keepAliveThread = new KeepAliveThread(slave,
		             this._keepAliveQueue, this._inputQueue, new ConnectionState(true));
		        
				//Inicio del hilo de keepAlive
		        this._keepAliveThread.start();
	}
}

