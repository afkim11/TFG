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
 * <p>Clase Java para PropiedadesGlobales complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="PropiedadesGlobales">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="intervaloMonitorizacionGestores" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="activarPanelTrazasDebug" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="listaPropiedades" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}ListaPropiedades" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PropiedadesGlobales", propOrder = {
    "intervaloMonitorizacionGestores",
    "activarPanelTrazasDebug",
    "listaPropiedades"
})
public class PropiedadesGlobales {

    protected Integer intervaloMonitorizacionGestores;
    protected Boolean activarPanelTrazasDebug;
    protected ListaPropiedades listaPropiedades;

    /**
     * Obtiene el valor de la propiedad intervaloMonitorizacionGestores.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIntervaloMonitorizacionGestores() {
        return intervaloMonitorizacionGestores;
    }

    /**
     * Define el valor de la propiedad intervaloMonitorizacionGestores.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIntervaloMonitorizacionGestores(Integer value) {
        this.intervaloMonitorizacionGestores = value;
    }

    /**
     * Obtiene el valor de la propiedad activarPanelTrazasDebug.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isActivarPanelTrazasDebug() {
        return activarPanelTrazasDebug;
    }

    /**
     * Define el valor de la propiedad activarPanelTrazasDebug.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setActivarPanelTrazasDebug(Boolean value) {
        this.activarPanelTrazasDebug = value;
    }

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

}
