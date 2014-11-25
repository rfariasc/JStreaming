public class KillProcessThread extends Thread {
	private Process process = null;
	private BoolObj isStop;

	public KillProcessThread(Process process, BoolObj isStop) {
		this.isStop = isStop;
		this.process = process;
	}

	@Override
	public void run() {
		System.out.println("Start thread Kill!!");
		while (!isStop.value) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (process != null) {
			process.destroy();
			System.out.println("Destroy process");
		}

	}

}
