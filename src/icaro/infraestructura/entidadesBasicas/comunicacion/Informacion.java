package icaro.infraestructura.entidadesBasicas.comunicacion;

public class Informacion implements Cloneable{
	protected Object contenido;
	
	public Informacion(Object cont){
		this.contenido=cont;
	}
	public Informacion(Object cont, Object cont2){
		this.contenido=cont;
	}
	public Object getContenido(){
		return this.contenido;
	}
	public void setContenido(Object cont){
		this.contenido=cont;
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
