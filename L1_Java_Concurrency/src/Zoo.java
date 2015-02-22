
// Resolves in answer b) Fehlermeldung beim Kompilieren.
// Zoo doesn't use interface Runnable
public class Zoo implements Runnable{
	static String a;
	
	public static void main(String args[]) {
		Zoo zoo = new Zoo();
	}
	
	Zoo() {
		a = "alpha";
		Thread t = new Thread((Runnable) this);
		t.start();
		Thread t1 = new Thread((Runnable) this);
		a = "Zebra";
		t1.start();
	}
	
	public void run() {
		for (int i=0; i<5; i++) {
			System.out.println(a);
		}
	}
}
