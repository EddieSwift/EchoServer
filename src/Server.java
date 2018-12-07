import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {

		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(39877);
		System.out.println("Got port");

		while (true) {

			System.out.println("Waiting new client!");
			Socket socket = serverSocket.accept();
			System.out.println("Client exist!");

			InputStream inputSource = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();

			InputStreamReader inputStreamReader = new InputStreamReader(inputSource);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

			while (socket.isConnected()) {

				String line = null;

				try {

					line = bufferedReader.readLine();

				} catch (IOException exception) {

					exception.printStackTrace();
					break;
				}

				if (line != null && line.contains("exit")) {

					break;
				}

				System.out.println("Client request: " + line);

				try {

					bufferedWriter.write("Simon said: " + line);
					bufferedWriter.newLine();
					bufferedWriter.flush();
				} catch (Exception exception) {

					exception.printStackTrace();
					break;
				}
			}

			bufferedWriter.close();
			bufferedReader.close();
			socket.close();
		}
	}
}

