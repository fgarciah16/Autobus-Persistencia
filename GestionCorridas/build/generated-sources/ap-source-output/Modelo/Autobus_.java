package Modelo;

import Modelo.Corridas;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-20T21:15:35")
@StaticMetamodel(Autobus.class)
public class Autobus_ { 

    public static volatile SingularAttribute<Autobus, String> tipo;
    public static volatile ListAttribute<Autobus, Corridas> corridasList;
    public static volatile SingularAttribute<Autobus, Integer> asientos;
    public static volatile SingularAttribute<Autobus, Integer> idautobus;

}