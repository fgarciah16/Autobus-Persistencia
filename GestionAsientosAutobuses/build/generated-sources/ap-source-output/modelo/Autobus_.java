package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Corridas;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-17T00:27:22")
@StaticMetamodel(Autobus.class)
public class Autobus_ { 

    public static volatile SingularAttribute<Autobus, String> tipo;
    public static volatile ListAttribute<Autobus, Corridas> corridasList;
    public static volatile SingularAttribute<Autobus, Integer> asientos;
    public static volatile SingularAttribute<Autobus, Integer> idautobus;

}