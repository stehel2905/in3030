import java.util.Arrays;
import java.util.Random;

public class testMerge {
	public static void main(String[]args) {
		int tallet = 10000000;
		int[] randArr = new int[tallet];
		int[] paraArr = new int[tallet];
		int[] sekArr = new int[tallet];
		Random ran = new Random();
		for(int i = 0; i<tallet; i++) {
			randArr[i]= ran.nextInt();
		}
		double[]paratider = new double[7];
		for(int i = 0; i<7; i++) {
			System.arraycopy(randArr, 0, paraArr, 0, tallet);
			double t1 = System.currentTimeMillis();
			ParaMerge pm = new ParaMerge(paraArr);
			double t2 = System.currentTimeMillis();
			double tottid = t2-t1;
			paratider[i]= tottid;
		}
		double[]sektider = new double[7];
		for(int i = 0; i<7; i++) {
			System.arraycopy(randArr, 0, sekArr, 0, tallet);
			double t1 = System.currentTimeMillis();
			mergesorting sm = new mergesorting(sekArr);
			double t2 = System.currentTimeMillis();
			double tottid = t2-t1;
			sektider[i]= tottid;
		}
		Arrays.sort(paratider);
		Arrays.sort(sektider);
		System.out.println("Gjennomsnittstid parallell: " + paratider[3]);
		System.out.println("Gjennomsnittstid sekvensiell: " + sektider[3]);
		System.out.println("Speedup: " + sektider[3]/paratider[3]);
		boolean riktig = true;
		for(int i=0;i<tallet-1;i++) {
			if(paraArr[i]>paraArr[i+1]) {
				riktig = false;
			}
		}
		System.out.println("\nParallell array var sortert riktig: " + riktig);
	}
	
}
