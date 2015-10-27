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
 * <p>Clase Java para DescComportamientoAgentes complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="DescComportamientoAgentes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DescComportamientoGestores" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}DescComportamientoGestores"/>
 *         &lt;element name="DescComportamientoAgentesAplicacion" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}DescComportamientoAgentesAplicacion"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DescComportamientoAgentes", propOrder = {
    "descComportamientoGestores",
    "descComportamientoAgentesAplicacion"
})
public class DescComportamientoAgentes {

    @XmlElement(name = "DescComportamientoGestores", required = true)
    protected DescComportamientoGestores descComportamientoGestores;
    @XmlElement(name = "DescComportamientoAgentesAplicacion", required = true)
    protected DescComportamientoAgentesAplicacion descComportamientoAgentesAplicacion;

    /**
     * Obtiene el valor de la propiedad descComportamientoGestores.
     * 
     * @return
     *     possible object is
     *     {@link DescComportamientoGestores }
     *     
     */
    public DescComportamientoGestores getDescComportamientoGestores() {
        return descComportamientoGestores;
    }

    /**
     * Define el valor de la propiedad descComportamientoGestores.
     * 
     * @param value
     *     allowed object is
     *     {@link DescComportamientoGestores }
     *     
     */
    public void setDescComportamientoGestores(DescComportamientoGestores value) {
        this.descComportamientoGestores = value;
    }

    /**
     * Obtiene el valor de la propiedad descComportamientoAgentesAplicacion.
     * 
     * @return
     *     possible object is
     *     {@link DescComportamientoAgentesAplicacion }
     *     
     */
    public DescComportamientoAgentesAplicacion getDescComportamientoAgentesAplicacion() {
        return descComportamientoAgentesAplicacion;
    }

    /**
     * Define el valor de la propiedad descComportamientoAgentesAplicacion.
     * 
     * @param value
     *     allowed object is
     *     {@link DescComportamientoAgentesAplicacion }
     *     
     */
    public void setDescComportamientoAgentesAplicacion(DescComportamientoAgentesAplicacion value) {
        this.descComportamientoAgentesAplicacion = value;
    }

}
