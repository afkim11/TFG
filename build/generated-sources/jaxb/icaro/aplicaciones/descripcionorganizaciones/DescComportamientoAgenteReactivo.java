//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.5-2 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: PM.10.07 a las 01:34:06 PM CEST 
//


package icaro.aplicaciones.descripcionorganizaciones;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para DescComportamientoAgenteReactivo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="DescComportamientoAgenteReactivo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:icaro:aplicaciones:descripcionOrganizaciones}DescComportamientoAgente">
 *       &lt;sequence>
 *         &lt;element name="rutaAutomata" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rutaAcciones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DescComportamientoAgenteReactivo", propOrder = {
    "rutaAutomata",
    "rutaAcciones"
})
public class DescComportamientoAgenteReactivo
    extends DescComportamientoAgente
{

    protected String rutaAutomata;
    protected String rutaAcciones;

    /**
     * Obtiene el valor de la propiedad rutaAutomata.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRutaAutomata() {
        return rutaAutomata;
    }

    /**
     * Define el valor de la propiedad rutaAutomata.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRutaAutomata(String value) {
        this.rutaAutomata = value;
    }

    /**
     * Obtiene el valor de la propiedad rutaAcciones.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRutaAcciones() {
        return rutaAcciones;
    }

    /**
     * Define el valor de la propiedad rutaAcciones.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRutaAcciones(String value) {
        this.rutaAcciones = value;
    }

}
