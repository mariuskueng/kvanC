
// Resolves in answer c) 100 solange das Programm läuft.
public class Master { 
	boolean cont = false;

	public static void main(String argv[]) { 
		Master m = new Master();
		m.go(); 
	}

	public void go() {
		Slave s = new Slave(this);
		Thread t1 = new Thread(s); 
		t1.start();
		while (!cont) {
		}
		s.setPrice(200);
	}
} 


class Slave implements Runnable { 
	int price = 100;
       Master master;
       
       Slave(Master m) {
    	   master = m;
       }
       
       synchronized public void setPrice(int price) {
    	   this.price = price;
       }
       
       synchronized public void run() { 
    	   master.cont = true;
       
    	   while (true) { 
    		   System.out.println(price);
    	   }
       } 
}