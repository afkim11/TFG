//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.5-2 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: PM.10.07 a las 01:34:06 PM CEST 
//


package icaro.aplicaciones.descripcionorganizaciones;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Instancia complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Instancia">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="listaPropiedades" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}ListaPropiedades" minOccurs="0"/>
 *         &lt;element name="nodoEspecifico" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}Nodo" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="refDescripcion" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Instancia", propOrder = {
    "listaPropiedades",
    "nodoEspecifico"
})
@XmlSeeAlso({
    InstanciaGestor.class
})
public class Instancia {

    protected ListaPropiedades listaPropiedades;
    protected Nodo nodoEspecifico;
    @XmlAttribute(name = "id", required = true)
    protected String id;
    @XmlAttribute(name = "refDescripcion", required = true)
    protected String refDescripcion;

    /**
     * Obtiene el valor de la propiedad listaPropiedades.
     * 
     * @return
     *     possible object is
     *     {@link ListaPropiedades }
     *     
     */
    public ListaPropiedades getListaPropiedades() {
        return listaPropiedades;
    }

    /**
     * Define el valor de la propiedad listaPropiedades.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaPropiedades }
     *     
     */
    public void setListaPropiedades(ListaPropiedades value) {
        this.listaPropiedades = value;
    }

    /**
     * Obtiene el valor de la propiedad nodoEspecifico.
     * 
     * @return
     *     possible object is
     *     {@link Nodo }
     *     
     */
    public Nodo getNodoEspecifico() {
        return nodoEspecifico;
    }

    /**
     * Define el valor de la propiedad nodoEspecifico.
     * 
     * @param value
     *     allowed object is
     *     {@link Nodo }
     *     
     */
    public void setNodoEspecifico(Nodo value) {
        this.nodoEspecifico = value;
    }

    /**
     * Obtiene el valor de la propiedad id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Obtiene el valor de la propiedad refDescripcion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefDescripcion() {
        return refDescripcion;
    }

    /**
     * Define el valor de la propiedad refDescripcion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefDescripcion(String value) {
        this.refDescripcion = value;
    }

}
