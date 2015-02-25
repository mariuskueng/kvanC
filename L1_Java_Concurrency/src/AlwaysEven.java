
public class AlwaysEven {
    private int i = 0;
    
    private void next() { 
        i++;
        i++;
    }
    
    private synchronized int getValue() { 
        return i;
    }
    
    public static void main(String[] args) {
        final AlwaysEven ae = new AlwaysEven();
        
        Thread t1 = new Thread("Watcher") { 
            @Override
            public void run() { 
                while (true) {
                    int val = ae.getValue(); 
                    if (val % 2 != 0){
                        System.out.println(val);
                        System.exit(0); 
                    } else {
                        System.out.println(val);
                    }
                }
            }
        };
        
        t1.start();
        
        while (true) {
            synchronized (ae) {
            	ae.next();
			}
        }
    }
}
