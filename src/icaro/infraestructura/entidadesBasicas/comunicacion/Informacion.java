package icaro.infraestructura.entidadesBasicas.comunicacion;

public class Informacion {
	private Object contenido;
	
	public Informacion(Object cont){
		this.contenido=cont;
	}
	public Object getContenido(){
		return this.contenido;
	}
	public void setContenido(Object cont){
		this.contenido=cont;
	}
}
