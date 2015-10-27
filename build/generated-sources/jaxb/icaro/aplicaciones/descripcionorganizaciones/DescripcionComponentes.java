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
 * <p>Clase Java para DescripcionComponentes complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="DescripcionComponentes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DescComportamientoAgentes" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}DescComportamientoAgentes"/>
 *         &lt;element name="DescRecursosAplicacion" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}DescRecursosAplicacion"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DescripcionComponentes", propOrder = {
    "descComportamientoAgentes",
    "descRecursosAplicacion"
})
public class DescripcionComponentes {

    @XmlElement(name = "DescComportamientoAgentes", required = true)
    protected DescComportamientoAgentes descComportamientoAgentes;
    @XmlElement(name = "DescRecursosAplicacion", required = true)
    protected DescRecursosAplicacion descRecursosAplicacion;

    /**
     * Obtiene el valor de la propiedad descComportamientoAgentes.
     * 
     * @return
     *     possible object is
     *     {@link DescComportamientoAgentes }
     *     
     */
    public DescComportamientoAgentes getDescComportamientoAgentes() {
        return descComportamientoAgentes;
    }

    /**
     * Define el valor de la propiedad descComportamientoAgentes.
     * 
     * @param value
     *     allowed object is
     *     {@link DescComportamientoAgentes }
     *     
     */
    public void setDescComportamientoAgentes(DescComportamientoAgentes value) {
        this.descComportamientoAgentes = value;
    }

    /**
     * Obtiene el valor de la propiedad descRecursosAplicacion.
     * 
     * @return
     *     possible object is
     *     {@link DescRecursosAplicacion }
     *     
     */
    public DescRecursosAplicacion getDescRecursosAplicacion() {
        return descRecursosAplicacion;
    }

    /**
     * Define el valor de la propiedad descRecursosAplicacion.
     * 
     * @param value
     *     allowed object is
     *     {@link DescRecursosAplicacion }
     *     
     */
    public void setDescRecursosAplicacion(DescRecursosAplicacion value) {
        this.descRecursosAplicacion = value;
    }

}
