package icaro.aplicaciones.Rosace.utils;

public class WaitSecond {
	
  public static void oneSec() {
     try {
       Thread.currentThread().sleep(1000);
       }
     catch (InterruptedException e) {
       e.printStackTrace();
       }
  }
  
  public static void manySec(long s) {
     try {
       Thread.currentThread().sleep(s * 1000);
       }
     catch (InterruptedException e) {
       e.printStackTrace();
       }
   }
  
  public static void manyMiliseconds(long ms) {
	     try {
	       Thread.currentThread().sleep(ms);
	     }
	     catch (InterruptedException e) {
	       e.printStackTrace();
	     }
  }
  
  
}