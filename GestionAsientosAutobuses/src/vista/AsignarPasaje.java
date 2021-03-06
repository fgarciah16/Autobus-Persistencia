/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import Pasajeros.DesignarPasajeros;
import acceso_datos.ReservaJpaController;
import java.awt.GridLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import modelo.Corridas;

import modelo.Reserva;

/**
 *
 * @author jesus
 */
public class AsignarPasaje extends javax.swing.JPanel {
private vista.Reserva reserva;
private Corridass corridas;
private Boletos boleto;
private Corridas corrida;
private ArrayList<Integer> ocupados;
private EntityManagerFactory emf;
private ReservaJpaController  controladorReserva;


    public AsignarPasaje() {
        initComponents();
        emf=Persistence.createEntityManagerFactory("GestionAsientosAutobusesPU");
        controladorReserva= new ReservaJpaController(emf);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        nombres = new Pasajeros.DesignarPasajeros();

        jLabel1.setText("Pasajeros");

        jButton1.setText("Imprimr Boletos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(nombres);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(79, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(278, 278, 278))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(243, 243, 243))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(47, 47, 47)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jButton1)
                .addContainerGap(34, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       corrida = corridas.getCorridas().get(corridas.getNumeroCorridaSeleccionada());
        int nb = reserva.getModelDatos().getRowCount();//Número de Boletos
        System.out.println("El número de boletos seleccionados es: "+nb);
        JTextArea boletos[]= new JTextArea[nb];
        boleto.setLayout(new GridLayout(0,4));
        //lista que contiene los boletos ocupados
        ocupados=reserva.getSelAsi().getSeleccionados();
        if(boleto.getComponentCount()>0)
            boleto.removeAll();
      
        for(int b=0;b<nb;b++){
            int asiento= (int)reserva.getModelDatos().getValueAt(b, 0);//numero de asiento
            boletos[b]=new JTextArea();
            boletos[b].setEditable(false);
            DateFormat formatoFecha= new SimpleDateFormat("dd/MM/yyyy");
	    String f=formatoFecha.format(corrida.getFechaHora());//fecha
	    DateFormat formatoHora= new SimpleDateFormat("HH:mm");
	    String h=formatoHora.format(corrida.getFechaHora());//Hora
	    String costo=""+corrida.getCosto();
            
            String pasajero=""+reserva.getModelDatos().getValueAt(b, 1);
            String tipoP=""+reserva.getModelDatos().getValueAt(b, 2);//Tipo de Pasajero
            
            ocupados.add((Integer)reserva.getModelDatos().getValueAt(b, 0));
            boletos[b].append("Viaje: "+corrida.getOrigen().getNombre()+"\n --> "+corrida.getDestino().getNombre() 
            +" \nFecha: " +f +" \nHora: "+h+" \nAsiento: "+ asiento+" \n\nPasajero: "+pasajero+" \nTipo pasaj."+tipoP+ "\nPrecio:"+costo+"\n\n");
            boleto.add(boletos[b]); 
           //Agregar los registros de los asientos a la base de datos
            Reserva r= new Reserva();
            r.setNum(asiento);
            r.setIdcorrida(corrida);
            controladorReserva.create(r);
        }
       reserva.getSelAsi().setAsignar(ocupados);  
    }//GEN-LAST:event_jButton1ActionPerformed

    
    public void conectaReserva(vista.Reserva reserva){
       this.reserva = reserva;
    }
    public void conectaCorridas(Corridass corridas){
       this.corridas=corridas;
    }
   public void conectaBoleto(Boletos boleto){
       this.boleto=boleto;
   }
   public DesignarPasajeros getNombres(){
       return nombres;
   }
   public void imprimirMensaje(String mensaje){
       JOptionPane.showMessageDialog(null,mensaje);
   }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private Pasajeros.DesignarPasajeros nombres;
    // End of variables declaration//GEN-END:variables
}
