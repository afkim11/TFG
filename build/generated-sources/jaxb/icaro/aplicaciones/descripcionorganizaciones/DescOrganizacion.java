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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para DescOrganizacion complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="DescOrganizacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PropiedadesGlobales" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}PropiedadesGlobales"/>
 *         &lt;element name="NodosDespliegue" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}ListaNodosEjecucion" minOccurs="0"/>
 *         &lt;element name="DescripcionComponentes" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}DescripcionComponentes"/>
 *         &lt;element name="DescInstancias" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}DescInstancias"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DescOrganizacion", propOrder = {
    "propiedadesGlobales",
    "nodosDespliegue",
    "descripcionComponentes",
    "descInstancias"
})
public class DescOrganizacion {

    @XmlElement(name = "PropiedadesGlobales", required = true)
    protected PropiedadesGlobales propiedadesGlobales;
    @XmlElement(name = "NodosDespliegue")
    protected ListaNodosEjecucion nodosDespliegue;
    @XmlElement(name = "DescripcionComponentes", required = true)
    protected DescripcionComponentes descripcionComponentes;
    @XmlElement(name = "DescInstancias", required = true)
    protected DescInstancias descInstancias;

    /**
     * Obtiene el valor de la propiedad propiedadesGlobales.
     * 
     * @return
     *     possible object is
     *     {@link PropiedadesGlobales }
     *     
     */
    public PropiedadesGlobales getPropiedadesGlobales() {
        return propiedadesGlobales;
    }

    /**
     * Define el valor de la propiedad propiedadesGlobales.
     * 
     * @param value
     *     allowed object is
     *     {@link PropiedadesGlobales }
     *     
     */
    public void setPropiedadesGlobales(PropiedadesGlobales value) {
        this.propiedadesGlobales = value;
    }

    /**
     * Obtiene el valor de la propiedad nodosDespliegue.
     * 
     * @return
     *     possible object is
     *     {@link ListaNodosEjecucion }
     *     
     */
    public ListaNodosEjecucion getNodosDespliegue() {
        return nodosDespliegue;
    }

    /**
     * Define el valor de la propiedad nodosDespliegue.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaNodosEjecucion }
     *     
     */
    public void setNodosDespliegue(ListaNodosEjecucion value) {
        this.nodosDespliegue = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcionComponentes.
     * 
     * @return
     *     possible object is
     *     {@link DescripcionComponentes }
     *     
     */
    public DescripcionComponentes getDescripcionComponentes() {
        return descripcionComponentes;
    }

    /**
     * Define el valor de la propiedad descripcionComponentes.
     * 
     * @param value
     *     allowed object is
     *     {@link DescripcionComponentes }
     *     
     */
    public void setDescripcionComponentes(DescripcionComponentes value) {
        this.descripcionComponentes = value;
    }

    /**
     * Obtiene el valor de la propiedad descInstancias.
     * 
     * @return
     *     possible object is
     *     {@link DescInstancias }
     *     
     */
    public DescInstancias getDescInstancias() {
        return descInstancias;
    }

    /**
     * Define el valor de la propiedad descInstancias.
     * 
     * @param value
     *     allowed object is
     *     {@link DescInstancias }
     *     
     */
    public void setDescInstancias(DescInstancias value) {
        this.descInstancias = value;
    }

}
