import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FindMax {
	static int GLOBAL_MAX = Integer.MIN_VALUE;
	static int GLOBAL_MIN = Integer.MAX_VALUE;
	static int kjerner = Runtime.getRuntime().availableProcessors();
	public static class myRunnable implements Runnable{
		int id, elem;
		int LOK_MIN = Integer.MAX_VALUE;
		int LOK_MAX = Integer.MIN_VALUE;
		static int[]arr;
		static SemCyclicBarrier cb;
		public myRunnable(int id, int elem) {
			this.id = id;
			this.elem = elem;
		}
		@Override
		public void run() {
			if(id == 1 || id ==2) {			
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			int start = elem/kjerner*id;
			int slutt = elem/kjerner*(id+1);
			//System.out.println("Start: " + start + " Slutt: " + slutt);
			for(int i = start; i <slutt; i++) {
				if(arr[i]<LOK_MIN) {
					LOK_MIN = arr[i];
				}
				if(arr[i]>LOK_MAX) {
					LOK_MAX = arr[i];
				}
			}
			setMinMax(LOK_MIN, LOK_MAX);
			int fakeMax = GLOBAL_MAX;
			cb.await();
			int max = GLOBAL_MAX;
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Jeg er traad " + id + " og jeg tror global max er " + max + " foer synk trodde jeg det var " + fakeMax );
			for(int i = start; i<slutt; i++) {
				arr[i] = arr[i]-GLOBAL_MAX;
			}
			cb.await();
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i = start; i <slutt; i++) {
				if(arr[i]<LOK_MIN) {
					LOK_MIN = arr[i];
				}
				if(arr[i]>LOK_MAX) {
					LOK_MAX = arr[i];
				}
			}
			setMinMax(LOK_MIN, LOK_MAX);
			cb.await();
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		static void setArr(int[]arr) {
			myRunnable.arr = arr;
		}
		static void setBarr(SemCyclicBarrier cb) {
			myRunnable.cb = cb;
		}
	}
	
	public static void main(String[]args) {
		Random rand = new Random();
		int[]arr = new int[100000];
		for(int i = 0; i<100000; i++) {
			arr[i]= rand.nextInt(1000000000);
		}
		SemCyclicBarrier cb = new SemCyclicBarrier(kjerner+1);
		myRunnable.setBarr(cb);
		myRunnable.setArr(arr);
	
		int kjerner = Runtime.getRuntime().availableProcessors();
		Thread[] traader = new Thread[kjerner];
		for(int i = 0; i<kjerner; i++) {
			myRunnable mr = new myRunnable(i, 100000);
			traader[i]= new Thread(mr);
			traader[i].start();
		}
		cb.await();
		cb.await();
		cb.await();
		System.out.println("GLOBAL_MAX og GLOBAL_MIN var henholdsvis: " + GLOBAL_MAX + GLOBAL_MIN + " etter alle operasjoner");
		
	}
	static synchronized void setMinMax(int min, int max) {
		if(min<GLOBAL_MIN) {
			GLOBAL_MIN = min;
		}
		if(max>GLOBAL_MAX) {
			GLOBAL_MAX = max;
		}
	}
}
