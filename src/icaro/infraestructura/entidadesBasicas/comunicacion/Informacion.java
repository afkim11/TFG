package icaro.infraestructura.entidadesBasicas.comunicacion;

public class Informacion {
	private Object contenido;
	private Object contenido2;
	
	public Informacion(Object cont){
		this.contenido=cont;
	}
	public Informacion(Object cont, Object cont2){
		this.contenido=cont;
		this.contenido2 = cont2;
	}
	public Object getContenido1(){
		return this.contenido;
	}
	public void setContenido1(Object cont){
		this.contenido=cont;
	}
	public Object getContenido2(){
		return this.contenido2;
	}
	public void setContenido2(Object cont){
		this.contenido2=cont;
	}
}
