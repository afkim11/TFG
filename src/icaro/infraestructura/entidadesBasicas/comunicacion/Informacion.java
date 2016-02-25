package icaro.infraestructura.entidadesBasicas.comunicacion;

public class Informacion implements Cloneable{
	protected Object contenido;
	protected Object contenido2;
	
	public Informacion(Object cont){
		this.contenido=cont;
	}
	public Informacion(Object cont, Object cont2){
		this.contenido=cont;
		this.contenido2=cont2;
	}
	public Object getContenido(){
		return this.contenido;
	}
	public Object getContenido2(){
		return this.contenido2;
	}
	public void setContenido(Object cont){
		this.contenido=cont;
	}
	public void setContenido2(Object cont){
		this.contenido2=cont;
	}
	public Informacion clone(){
		try { 
			return (Informacion) super.clone(); 
		} catch(CloneNotSupportedException e) { 
			System.out.println("Cloning not allowed."); 
			return this; 
		} 
	}
		
}
