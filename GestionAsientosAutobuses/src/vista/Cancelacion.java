/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import acceso_datos.ReservaJpaController;
import acceso_datos.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.SpinnerListModel;
import modelo.Corridas;
import modelo.Reserva;

/**
 *
 * @author jesus
 */
public class Cancelacion extends javax.swing.JPanel {

    private Corridass corridas;
    private vista.Reserva reserva;
    private List<Corridas> info;
    private ArrayList<Integer> ocupados; //Array paara los asientos ocupados 
    private Corridas corri;
    private EntityManagerFactory emf;
    private ReservaJpaController ReservasControl;
    
    
    public Cancelacion() {
        initComponents();
         emf=Persistence.createEntityManagerFactory("GestionAsientosAutobusesPU");
         ReservasControl= new  ReservaJpaController(emf);
    }

    public void conectaCorridas(Corridass corridas){
        this.corridas=corridas;
        inicializarMetodo();
    }
    public void conectaReserva(vista.Reserva reserva){
        this.reserva=reserva;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        NumCorrida = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        asiento = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        NumCorrida.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        NumCorrida.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NumCorridaItemStateChanged(evt);
            }
        });
        NumCorrida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NumCorridaActionPerformed(evt);
            }
        });

        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Cancelacion");

        jLabel2.setText("Corrida :");

        jLabel3.setText("Asiento a cancelar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(266, 266, 266))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(253, 253, 253)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(asiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(187, 187, 187)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(NumCorrida, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(134, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NumCorrida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(asiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(30, 30, 30)
                .addComponent(jButton1)
                .addContainerGap(184, Short.MAX_VALUE))
        );

        ArrayList<String> lista= new ArrayList<String>();
        lista.add("");
        SpinnerListModel modelo= new SpinnerListModel(lista);
        asiento.setModel(modelo);
    }// </editor-fold>//GEN-END:initComponents

    private void NumCorridaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NumCorridaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NumCorridaActionPerformed

    private void NumCorridaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NumCorridaItemStateChanged
SpinnerListModel modelo= new SpinnerListModel();
        ArrayList<String> lista= new ArrayList<String>();
        lista.add("");
        int numCorSel= NumCorrida.getSelectedIndex();  //Número de Corrida Seleccionada 
        System.out.println(" Columna Seleccionada es:" + numCorSel);
        
        if(numCorSel>0){
        System.out.println(" Columna Seleccionada es:" + numCorSel);
        corri = info.get(numCorSel-1);
        
        
        System.out.println("Origen: "+corri.getOrigen().getNombre()+" Destino: "+corri.getDestino().getNombre() );
        ocupados = getAsientosUsados(corri);
        System.out.println("asientos ocupados:" + ocupados.size());
        if(ocupados.size()>0){ 
        ocupados.sort(new Comparar());
        modelo= new SpinnerListModel(ocupados);
        asiento.setModel(modelo);
        asiento.setEnabled(true);
        
        }else {
           asiento.setEnabled(false);
           modelo.setList(lista);
           asiento.setModel(modelo);
        }
    }else{
           asiento.setEnabled(false);
           modelo.setList(lista);
           asiento.setModel(modelo);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_NumCorridaItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(asiento.isEnabled()){
            int numAsiento=(int)asiento.getValue();
            String line= "Asiento cancelado: "+numAsiento+" \n corrida: "+corri.getIdcorrida()+"  "+corri.getOrigen().getNombre()+" a "+corri.getDestino().getNombre();
            
            ocupados=getAsientosUsados(corri);
            List<Reserva> asientosR= ReservasControl.findReservaEntities();
            for(Reserva r: asientosR){
                if(r.getIdcorrida().getIdcorrida()==corri.getIdcorrida()&&r.getNum()==numAsiento){
                    try {
                        ReservasControl.destroy(r.getIdre());
                        break;
                    } catch (NonexistentEntityException ex) {
                        Logger.getLogger(Cancelacion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            for(int i=0;i<ocupados.size();i++){
                if(ocupados.get(i)==numAsiento){
                    ocupados.remove(i);
                    break;
                }
              }
            SpinnerListModel model= new SpinnerListModel();
            model.setList(ocupados);  
            reserva.getSelAsi().setCancelar(numAsiento);
            MensajeAuxiliar(line);
            asiento.setModel(model);
         }
    }//GEN-LAST:event_jButton1ActionPerformed
    
    public void inicializarMetodo(){
    info=corridas.getCorridas();
    String line="";
    NumCorrida.removeAllItems();
    line="";
    NumCorrida.addItem(line);
    for(int i=0;i<info.size();i++){
        line="Corrida: "+ info.get(i).getOrigen().getNombre()+" a "+info.get(i).getDestino().getNombre();
        NumCorrida.addItem(line);
    }

}
public ArrayList<Integer> getAsientosUsados(modelo.Corridas c){
        List<Reserva> res=ReservasControl.findReservaEntities();
        ArrayList<Integer> asientosO=new ArrayList<Integer>();
             for(Reserva r1 : res){
                if(c.getIdcorrida()==r1.getIdcorrida().getIdcorrida()){
                        asientosO.add(r1.getNum());
                }
             }
             return asientosO;
    }

//public ArrayList<String>getOrigenDes(modelo.Corridas b){
//    List<Reserva> resul = ReservasControl.findReservaEntities();
//    ArrayLista<String>OrigenD = ArrayList<String>();
//    
//}
public void MensajeAuxiliar(String mensaje){
    JOptionPane.showMessageDialog(null,mensaje);
}

private class Comparar implements Comparator<Integer>{

        @Override
        public int compare(Integer a, Integer b) {
           return a-b;
        }
    
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> NumCorrida;
    private javax.swing.JSpinner asiento;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
