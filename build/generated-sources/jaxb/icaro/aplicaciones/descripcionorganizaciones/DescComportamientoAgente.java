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
 * <p>Clase Java para DescComportamientoAgente complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="DescComportamientoAgente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="nombreComportamiento" use="required" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}NombreAgente" />
 *       &lt;attribute name="localizacionComportamiento" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tipo" use="required" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}TipoAgente" />
 *       &lt;attribute name="rol" use="required" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}RolAgente" />
 *       &lt;attribute name="localizacionClaseAcciones" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="localizacionFicheroReglas" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="localizacionFicheroAutomata" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DescComportamientoAgente")
@XmlSeeAlso({
    DescComportamientoAgenteCognitivo.class,
    DescComportamientoAgenteReactivo.class
})
public class DescComportamientoAgente {

    @XmlAttribute(name = "nombreComportamiento", required = true)
    protected String nombreComportamiento;
    @XmlAttribute(name = "localizacionComportamiento")
    protected String localizacionComportamiento;
    @XmlAttribute(name = "tipo", required = true)
    protected TipoAgente tipo;
    @XmlAttribute(name = "rol", required = true)
    protected RolAgente rol;
    @XmlAttribute(name = "localizacionClaseAcciones")
    protected String localizacionClaseAcciones;
    @XmlAttribute(name = "localizacionFicheroReglas")
    protected String localizacionFicheroReglas;
    @XmlAttribute(name = "localizacionFicheroAutomata")
    protected String localizacionFicheroAutomata;

    /**
     * Obtiene el valor de la propiedad nombreComportamiento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreComportamiento() {
        return nombreComportamiento;
    }

    /**
     * Define el valor de la propiedad nombreComportamiento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreComportamiento(String value) {
        this.nombreComportamiento = value;
    }

    /**
     * Obtiene el valor de la propiedad localizacionComportamiento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalizacionComportamiento() {
        return localizacionComportamiento;
    }

    /**
     * Define el valor de la propiedad localizacionComportamiento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalizacionComportamiento(String value) {
        this.localizacionComportamiento = value;
    }

    /**
     * Obtiene el valor de la propiedad tipo.
     * 
     * @return
     *     possible object is
     *     {@link TipoAgente }
     *     
     */
    public TipoAgente getTipo() {
        return tipo;
    }

    /**
     * Define el valor de la propiedad tipo.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoAgente }
     *     
     */
    public void setTipo(TipoAgente value) {
        this.tipo = value;
    }

    /**
     * Obtiene el valor de la propiedad rol.
     * 
     * @return
     *     possible object is
     *     {@link RolAgente }
     *     
     */
    public RolAgente getRol() {
        return rol;
    }

    /**
     * Define el valor de la propiedad rol.
     * 
     * @param value
     *     allowed object is
     *     {@link RolAgente }
     *     
     */
    public void setRol(RolAgente value) {
        this.rol = value;
    }

    /**
     * Obtiene el valor de la propiedad localizacionClaseAcciones.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalizacionClaseAcciones() {
        return localizacionClaseAcciones;
    }

    /**
     * Define el valor de la propiedad localizacionClaseAcciones.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalizacionClaseAcciones(String value) {
        this.localizacionClaseAcciones = value;
    }

    /**
     * Obtiene el valor de la propiedad localizacionFicheroReglas.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalizacionFicheroReglas() {
        return localizacionFicheroReglas;
    }

    /**
     * Define el valor de la propiedad localizacionFicheroReglas.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalizacionFicheroReglas(String value) {
        this.localizacionFicheroReglas = value;
    }

    /**
     * Obtiene el valor de la propiedad localizacionFicheroAutomata.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalizacionFicheroAutomata() {
        return localizacionFicheroAutomata;
    }

    /**
     * Define el valor de la propiedad localizacionFicheroAutomata.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalizacionFicheroAutomata(String value) {
        this.localizacionFicheroAutomata = value;
    }

}
