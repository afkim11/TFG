//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.5-2 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: PM.10.07 a las 01:34:06 PM CEST 
//


package icaro.aplicaciones.descripcionorganizaciones;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Nodo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Nodo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nombreUso" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nombreCompletoHost" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Nodo", propOrder = {
    "nombreUso",
    "nombreCompletoHost"
})
public class Nodo {

    @XmlElement(required = true)
    protected String nombreUso;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String nombreCompletoHost;

    /**
     * Obtiene el valor de la propiedad nombreUso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreUso() {
        return nombreUso;
    }

    /**
     * Define el valor de la propiedad nombreUso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreUso(String value) {
        this.nombreUso = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreCompletoHost.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreCompletoHost() {
        return nombreCompletoHost;
    }

    /**
     * Define el valor de la propiedad nombreCompletoHost.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreCompletoHost(String value) {
        this.nombreCompletoHost = value;
    }

}
