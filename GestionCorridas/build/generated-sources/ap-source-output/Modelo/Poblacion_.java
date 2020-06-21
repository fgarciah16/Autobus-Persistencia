package Modelo;

import Modelo.Corridas;
import Modelo.Municipio;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-20T21:15:35")
@StaticMetamodel(Poblacion.class)
public class Poblacion_ { 

    public static volatile ListAttribute<Poblacion, Corridas> corridasList;
    public static volatile ListAttribute<Poblacion, Corridas> corridasList1;
    public static volatile SingularAttribute<Poblacion, Municipio> idmpo;
    public static volatile SingularAttribute<Poblacion, String> nombre;
    public static volatile SingularAttribute<Poblacion, Integer> idpob;

}