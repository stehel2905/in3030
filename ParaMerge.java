

public class ParaMerge {
	int kjerner = Runtime.getRuntime().availableProcessors();
	public ParaMerge(int[]arr) {
		Thread tx = new Thread(new worker(arr, arr.length));
		tx.start();
		try {
			tx.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	class worker implements Runnable{
		int[]arr;
		int len;
		public worker(int[]arr, int len) {
			this.arr = arr;
			this.len = len;
		}
		@Override
		public void run() {
			//System.out.println("Startet ny traad");
			mergeSort(arr, len);
		}
		void merge(int[] left_arr, int[] right_arr, int[] arr, int left_size, int right_size) {
	    	
	        int i = 0;
	        int l = 0;
	        int r = 0;
	        // The while loops check the conditions for merging
	        while (l < left_size && r < right_size) {
	            if (left_arr[l] < right_arr[r]) {
	                arr[i++] = left_arr[l++];
	            } else {
	                arr[i++] = right_arr[r++];
	            }
	        }
	        while (l < left_size) {
	            arr[i++] = left_arr[l++];
	        }
	        while (r < right_size) {
	            arr[i++] = right_arr[r++];
	        }
	    }
	public void mergeSort(int[] arr, int len) {
	    if (len < 2) {
	        return;
	    }

	    int mid = len / 2;
	    int[] left_arr = new int[mid];
	    int[] right_arr = new int[len - mid];

	    // Dividing array into two and copying into two separate arrays
	    int k = 0;
	    for (int i = 0; i < len; ++i) {
	        if (i < mid) {
	            left_arr[i] = arr[i];
	        } else {
	            right_arr[k] = arr[i];
	            k = k + 1;
	        }
	    }
	    // Recursively calling the function to divide the subarrays further
	    //Dersom det er "nok plass" til det, starter vi både høyre, og venstre-sort.
	    if(kjerner >1) {
	    	Thread t1 = new Thread(new worker(left_arr, mid));
	    	Thread t2 = new Thread(new worker(right_arr, len-mid));
	    	kjerner -=2;
	    	t1.start();
	    	t2.start();
	    	
	    	try {
				t1.join();
				t2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	merge(left_arr, right_arr, arr, mid, len-mid);
	    	
	    	
	    	
	    	
	    }
	    //dersom det ikke er "plass" til både høyre og venstre, men fortsatt noe plass, kjører vi kun venstre
	    else if(kjerner>0) {
	    	Thread t1 = new Thread(new worker(left_arr, mid));
	    	kjerner--;
	    	t1.start();
	    	mergeSort(right_arr, len-mid);
	    	try {
				t1.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	merge(left_arr, right_arr, arr, mid, len-mid);
	    	
	    	System.out.println("Kjerner: " + kjerner);
	    }
	    //dersom det ikke er noe "plass" i det hele tatt, rekurserer vi på vanlig vis.
	    else {
	    	mergeSort(left_arr, mid);
	        mergeSort(right_arr, len - mid);
	        // Calling the merge method on each subdivision
	        merge(left_arr, right_arr, arr, mid, len - mid);
	    }
	    
	    }
	    
	   
	}
}

