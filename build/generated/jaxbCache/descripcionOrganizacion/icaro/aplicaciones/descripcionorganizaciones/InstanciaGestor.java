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
 * <p>Clase Java para InstanciaGestor complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="InstanciaGestor">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:icaro:aplicaciones:descripcionOrganizaciones}Instancia">
 *       &lt;sequence>
 *         &lt;element name="componentesGestionados" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}ComponentesGestionados"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InstanciaGestor", propOrder = {
    "componentesGestionados"
})
public class InstanciaGestor
    extends Instancia
{

    @XmlElement(required = true)
    protected ComponentesGestionados componentesGestionados;

    /**
     * Obtiene el valor de la propiedad componentesGestionados.
     * 
     * @return
     *     possible object is
     *     {@link ComponentesGestionados }
     *     
     */
    public ComponentesGestionados getComponentesGestionados() {
        return componentesGestionados;
    }

    /**
     * Define el valor de la propiedad componentesGestionados.
     * 
     * @param value
     *     allowed object is
     *     {@link ComponentesGestionados }
     *     
     */
    public void setComponentesGestionados(ComponentesGestionados value) {
        this.componentesGestionados = value;
    }

}
