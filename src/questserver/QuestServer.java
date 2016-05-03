package questserver;

import processing.core.PApplet;
import processing.net.Client;
import processing.net.Server;

public class QuestServer extends PApplet {

	public static void main(String _args[]) {
		PApplet.main(new String[] { questserver.QuestServer.class.getName() });
	}

	Server mainServer;
	int clientConectedNum = 0;

	public void settings() {
		size(700, 600);
	}

	public void setup() {
		mainServer = new Server(this, 7835);
		mainServer.run();
		println("server is runing");
	}

	public void draw() {
		Client thisClient = mainServer.available();
		if (thisClient == null)
			return;
		String messgeFC = thisClient.readString();
		if (messgeFC == null)
			return;
		String type=messgeFC.substring(0, 2);
		int clientNum=Integer.parseInt(messgeFC.substring(3, 4));
		String messege=messgeFC.substring(5);
		if (type.equals("ss")) {
			println("[log] client num : " + clientNum + ", says: " + messege);
		}else if(type.equals("qq")){
			mainServer.disconnect(thisClient);
			println("[log] client number : "+clientNum+", has disconected");
		}
		
		
		background(0);
	}

	public void serverEvent(Server someServer, Client someClient) {
		println("[log] new conection number : " + ++clientConectedNum + " ip : " + someClient.ip());
		mainServer.write("nc " + clientConectedNum);
	}

	private void sendToClient(String type, int client, String messege) {
		mainServer.write(type + " " + client + " " + messege);
	}
}
