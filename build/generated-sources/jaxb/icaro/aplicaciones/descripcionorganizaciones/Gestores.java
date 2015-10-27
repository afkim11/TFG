//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.5-2 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: PM.10.07 a las 01:34:06 PM CEST 
//


package icaro.aplicaciones.descripcionorganizaciones;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Gestores complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Gestores">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InstanciaGestor" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}InstanciaGestor" maxOccurs="unbounded"/>
 *         &lt;element name="nodoComun" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}Nodo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Gestores", propOrder = {
    "instanciaGestor",
    "nodoComun"
})
public class Gestores {

    @XmlElement(name = "InstanciaGestor", required = true)
    protected List<InstanciaGestor> instanciaGestor;
    protected Nodo nodoComun;

    /**
     * Gets the value of the instanciaGestor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instanciaGestor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstanciaGestor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InstanciaGestor }
     * 
     * 
     */
    public List<InstanciaGestor> getInstanciaGestor() {
        if (instanciaGestor == null) {
            instanciaGestor = new ArrayList<InstanciaGestor>();
        }
        return this.instanciaGestor;
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
