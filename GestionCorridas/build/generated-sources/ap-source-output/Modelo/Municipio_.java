package Modelo;

import Modelo.Poblacion;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-20T21:15:35")
@StaticMetamodel(Municipio.class)
public class Municipio_ { 

    public static volatile SingularAttribute<Municipio, Integer> habitantes;
    public static volatile ListAttribute<Municipio, Poblacion> poblacionList;
    public static volatile SingularAttribute<Municipio, Integer> idmpo;
    public static volatile SingularAttribute<Municipio, String> nombre;

}