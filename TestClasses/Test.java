import java.util.ArrayList;
public class Test extends Test2 { 
	public static void main(String[] args){
		/*double q = 36.0/60;
		double qm = 30.0/60;
		
		double t = 52.0/90;
		double tm = 57.0/90;
		
		System.out.println((0.3*q + 0.4*t + 0.1)/0.8);
		System.out.println((0.3*qm + 0.4*tm + 0.1)/0.8);
		System.out.println(87.0/90);
		*/
		double test = (32+95.3+26+96.7+26+96 + 23)/(32+26+26+100+100 + 100 + 24);
		double assignment = 105.5/105;
		System.out.println(assignment + " " + test);
		System.out.println((0.6*test + 0.2*assignment)/0.8);
		
		System.out.println((68.5+16.5)/(74+20));
	}
	public static void interesting(int n) {
		System.out.println(n + " ");
		if (n <= 1) System.out.println("repeat after me...");
		else interesting( (n-1)/3);
		System.out.print(n+ " ");
	}
}
