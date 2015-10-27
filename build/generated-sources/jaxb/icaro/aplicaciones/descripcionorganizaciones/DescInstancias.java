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
 * <p>Clase Java para DescInstancias complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="DescInstancias">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Gestores" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}Gestores"/>
 *         &lt;element name="AgentesAplicacion" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}AgentesAplicacion"/>
 *         &lt;element name="RecursosAplicacion" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}RecursosAplicacion"/>
 *         &lt;element name="nodoComun" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}Nodo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DescInstancias", propOrder = {
    "gestores",
    "agentesAplicacion",
    "recursosAplicacion",
    "nodoComun"
})
public class DescInstancias {

    @XmlElement(name = "Gestores", required = true)
    protected Gestores gestores;
    @XmlElement(name = "AgentesAplicacion", required = true)
    protected AgentesAplicacion agentesAplicacion;
    @XmlElement(name = "RecursosAplicacion", required = true)
    protected RecursosAplicacion recursosAplicacion;
    @XmlElement(required = true)
    protected Nodo nodoComun;

    /**
     * Obtiene el valor de la propiedad gestores.
     * 
     * @return
     *     possible object is
     *     {@link Gestores }
     *     
     */
    public Gestores getGestores() {
        return gestores;
    }

    /**
     * Define el valor de la propiedad gestores.
     * 
     * @param value
     *     allowed object is
     *     {@link Gestores }
     *     
     */
    public void setGestores(Gestores value) {
        this.gestores = value;
    }

    /**
     * Obtiene el valor de la propiedad agentesAplicacion.
     * 
     * @return
     *     possible object is
     *     {@link AgentesAplicacion }
     *     
     */
    public AgentesAplicacion getAgentesAplicacion() {
        return agentesAplicacion;
    }

    /**
     * Define el valor de la propiedad agentesAplicacion.
     * 
     * @param value
     *     allowed object is
     *     {@link AgentesAplicacion }
     *     
     */
    public void setAgentesAplicacion(AgentesAplicacion value) {
        this.agentesAplicacion = value;
    }

    /**
     * Obtiene el valor de la propiedad recursosAplicacion.
     * 
     * @return
     *     possible object is
     *     {@link RecursosAplicacion }
     *     
     */
    public RecursosAplicacion getRecursosAplicacion() {
        return recursosAplicacion;
    }

    /**
     * Define el valor de la propiedad recursosAplicacion.
     * 
     * @param value
     *     allowed object is
     *     {@link RecursosAplicacion }
     *     
     */
    public void setRecursosAplicacion(RecursosAplicacion value) {
        this.recursosAplicacion = value;
    }

    /**
     * Obtiene el valor de la propiedad nodoComun.
     * 
     * @return
     *     possible object is
     *     {@link Nodo }
     *     
     */
    public Nodo getNodoComun() {
        return nodoComun;
    }

    /**
     * Define el valor de la propiedad nodoComun.
     * 
     * @param value
     *     allowed object is
     *     {@link Nodo }
     *     
     */
    public void setNodoComun(Nodo value) {
        this.nodoComun = value;
    }

}
