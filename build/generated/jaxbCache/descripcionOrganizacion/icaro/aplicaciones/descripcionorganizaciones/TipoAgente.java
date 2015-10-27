//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.5-2 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: PM.10.07 a las 01:34:06 PM CEST 
//


package icaro.aplicaciones.descripcionorganizaciones;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para TipoAgente.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoAgente">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Cognitivo"/>
 *     &lt;enumeration value="ADO"/>
 *     &lt;enumeration value="DirigidoPorObjetivos"/>
 *     &lt;enumeration value="CognitivoConExpectativas"/>
 *     &lt;enumeration value="Reactivo"/>
 *     &lt;enumeration value="ReactivoSCXML"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoAgente")
@XmlEnum
public enum TipoAgente {

    @XmlEnumValue("Cognitivo")
    COGNITIVO("Cognitivo"),
    ADO("ADO"),
    @XmlEnumValue("DirigidoPorObjetivos")
    DIRIGIDO_POR_OBJETIVOS("DirigidoPorObjetivos"),
    @XmlEnumValue("CognitivoConExpectativas")
    COGNITIVO_CON_EXPECTATIVAS("CognitivoConExpectativas"),
    @XmlEnumValue("Reactivo")
    REACTIVO("Reactivo"),
    @XmlEnumValue("ReactivoSCXML")
    REACTIVO_SCXML("ReactivoSCXML");
    private final String value;

    TipoAgente(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoAgente fromValue(String v) {
        for (TipoAgente c: TipoAgente.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
