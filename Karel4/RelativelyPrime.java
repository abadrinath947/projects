public class RelativelyPrime{

	public static void main(String[] args){
		RelativelyPrime obj = new RelativelyPrime();
		System.out.println(obj.checkRelativelyPrime(20,100));
	}
	public boolean checkRelativelyPrime(int x, int y){
		for (int i = 2; i <= x; i++){
			if (x%i == 0 && y%i == 0) return false;
	}
	return true;
}


}
	
