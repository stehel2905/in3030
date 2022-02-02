import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemCyclicBarrier {
	Semaphore sem;
	int antall;
	boolean isListening = false;
	public SemCyclicBarrier(int antall) {
		this.antall = antall;
		sem = new Semaphore(0, true);
		
	}
	void await() {
		listen();
		sem.acquireUninterruptibly();
	}
	synchronized void listen() {
		//System.out.println(isListening);
		if(!isListening) {
			Thread listenerThread = new Thread(new listener());
			listenerThread.start();
			isListening = true;
			/*try {
				listenerThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		
	}
	
	class listener implements Runnable{

		@Override
		public void run() {
			while(sem.getQueueLength()!= antall) {
				
			}
			//System.out.println("Kjorte");
			sem.release(antall);
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sem.drainPermits();
				//System.out.println(sem.tryAcquire(antall, 1, TimeUnit.SECONDS));
				
				isListening = false;
				
		}
		
	}
}
