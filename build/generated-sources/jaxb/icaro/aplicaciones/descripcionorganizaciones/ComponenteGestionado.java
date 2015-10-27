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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ComponenteGestionado complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ComponenteGestionado">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="refId" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="refDescripcion" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tipoComponente" use="required" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}TipoComponente" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComponenteGestionado")
public class ComponenteGestionado {

    @XmlAttribute(name = "refId", required = true)
    protected String refId;
    @XmlAttribute(name = "refDescripcion")
    protected String refDescripcion;
    @XmlAttribute(name = "tipoComponente", required = true)
    protected TipoComponente tipoComponente;

    /**
     * Obtiene el valor de la propiedad refId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefId() {
        return refId;
    }

    /**
     * Define el valor de la propiedad refId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefId(String value) {
        this.refId = value;
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

    /**
     * Obtiene el valor de la propiedad tipoComponente.
     * 
     * @return
     *     possible object is
     *     {@link TipoComponente }
     *     
     */
    public TipoComponente getTipoComponente() {
        return tipoComponente;
    }

    /**
     * Define el valor de la propiedad tipoComponente.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoComponente }
     *     
     */
    public void setTipoComponente(TipoComponente value) {
        this.tipoComponente = value;
    }

}
