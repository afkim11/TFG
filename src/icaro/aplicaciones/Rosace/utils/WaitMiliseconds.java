package icaro.aplicaciones.Rosace.utils;

public class WaitMiliseconds {
	  
  public static void manyMiliseconds(long ms) {
	     try {
	       Thread.currentThread().sleep(ms);
	     }
	     catch (InterruptedException e) {
	       e.printStackTrace();
	     }
  }
  
}