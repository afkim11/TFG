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
 * <p>Clase Java para TipoComponente.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoComponente">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AgenteAplicacion"/>
 *     &lt;enumeration value="Gestor"/>
 *     &lt;enumeration value="RecursoAplicacion"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoComponente")
@XmlEnum
public enum TipoComponente {

    @XmlEnumValue("AgenteAplicacion")
    AGENTE_APLICACION("AgenteAplicacion"),
    @XmlEnumValue("Gestor")
    GESTOR("Gestor"),
    @XmlEnumValue("RecursoAplicacion")
    RECURSO_APLICACION("RecursoAplicacion");
    private final String value;

    TipoComponente(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoComponente fromValue(String v) {
        for (TipoComponente c: TipoComponente.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
