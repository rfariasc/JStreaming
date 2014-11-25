import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.prefs.BackingStoreException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.omg.PortableInterceptor.InterceptorOperations;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Client_GUI clientGUI = new Client_GUI();
		clientGUI.setDefaultCloseOperation(clientGUI.EXIT_ON_CLOSE);
		clientGUI.setVisible(true);
	}

}
