package icaro.aplicaciones.Rosace.informacion;

public class InformeVictimaMuerta {
	    public Victim victima;
	    public String nombreVictima;
	    public String referenciaContexto;
	    

	    public InformeVictimaMuerta(String  identEmisor,String ref){
	        nombreVictima = identEmisor;
	        referenciaContexto = ref;
	    }
	    public InformeVictimaMuerta(String  identEmisor,String refContexto,Victim contenido){
	        nombreVictima = identEmisor;
	        victima = contenido;
	        referenciaContexto = refContexto;
	    }
	    
	    public String getidentVictima() {
			return nombreVictima;
		}
	    
	    public Victim getVictima() {
	        return victima;
		}
	    public String getReferenciaContexto() {
			return referenciaContexto;
		}
}
