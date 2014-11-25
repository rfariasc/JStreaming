import java.io.*;
public class StreamGobbler extends Thread {
	InputStream is;
    boolean discard;
    BoolObj isStop;
    StreamGobbler(InputStream is, boolean discard, BoolObj isStop) {
      this.is = is;
      this.discard = discard;
      this.isStop = isStop;
    }

    public void run() {
      try {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line=null;
        while ( (line = br.readLine()) != null)
        	if(isStop.value)
        		return;
        	
          	if(!discard)
          		System.out.println(line);
        	
        }
      catch (IOException ioe) {
        ioe.printStackTrace();  
      }
    }
}
