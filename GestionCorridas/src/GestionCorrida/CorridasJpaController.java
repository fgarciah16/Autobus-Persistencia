/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionCorrida;

import GestionCorrida.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Autobus;
import Modelo.Corridas;
import Modelo.Poblacion;
import Modelo.Reserva;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jesus
 */
public class CorridasJpaController implements Serializable {

    public CorridasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Corridas corridas) {
        if (corridas.getReservaList() == null) {
            corridas.setReservaList(new ArrayList<Reserva>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Autobus autobus = corridas.getAutobus();
            if (autobus != null) {
                autobus = em.getReference(autobus.getClass(), autobus.getIdautobus());
                corridas.setAutobus(autobus);
            }
            Poblacion destino = corridas.getDestino();
            if (destino != null) {
                destino = em.getReference(destino.getClass(), destino.getIdpob());
                corridas.setDestino(destino);
            }
            Poblacion origen = corridas.getOrigen();
            if (origen != null) {
                origen = em.getReference(origen.getClass(), origen.getIdpob());
                corridas.setOrigen(origen);
            }
            List<Reserva> attachedReservaList = new ArrayList<Reserva>();
            for (Reserva reservaListReservaToAttach : corridas.getReservaList()) {
                reservaListReservaToAttach = em.getReference(reservaListReservaToAttach.getClass(), reservaListReservaToAttach.getIdre());
                attachedReservaList.add(reservaListReservaToAttach);
            }
            corridas.setReservaList(attachedReservaList);
            em.persist(corridas);
            if (autobus != null) {
                autobus.getCorridasList().add(corridas);
                autobus = em.merge(autobus);
            }
            if (destino != null) {
                destino.getCorridasList().add(corridas);
                destino = em.merge(destino);
            }
            if (origen != null) {
                origen.getCorridasList().add(corridas);
                origen = em.merge(origen);
            }
            for (Reserva reservaListReserva : corridas.getReservaList()) {
                Corridas oldIdcorridaOfReservaListReserva = reservaListReserva.getIdcorrida();
                reservaListReserva.setIdcorrida(corridas);
                reservaListReserva = em.merge(reservaListReserva);
                if (oldIdcorridaOfReservaListReserva != null) {
                    oldIdcorridaOfReservaListReserva.getReservaList().remove(reservaListReserva);
                    oldIdcorridaOfReservaListReserva = em.merge(oldIdcorridaOfReservaListReserva);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Corridas corridas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Corridas persistentCorridas = em.find(Corridas.class, corridas.getIdcorrida());
            Autobus autobusOld = persistentCorridas.getAutobus();
            Autobus autobusNew = corridas.getAutobus();
            Poblacion destinoOld = persistentCorridas.getDestino();
            Poblacion destinoNew = corridas.getDestino();
            Poblacion origenOld = persistentCorridas.getOrigen();
            Poblacion origenNew = corridas.getOrigen();
            List<Reserva> reservaListOld = persistentCorridas.getReservaList();
            List<Reserva> reservaListNew = corridas.getReservaList();
            if (autobusNew != null) {
                autobusNew = em.getReference(autobusNew.getClass(), autobusNew.getIdautobus());
                corridas.setAutobus(autobusNew);
            }
            if (destinoNew != null) {
                destinoNew = em.getReference(destinoNew.getClass(), destinoNew.getIdpob());
                corridas.setDestino(destinoNew);
            }
            if (origenNew != null) {
                origenNew = em.getReference(origenNew.getClass(), origenNew.getIdpob());
                corridas.setOrigen(origenNew);
            }
            List<Reserva> attachedReservaListNew = new ArrayList<Reserva>();
            for (Reserva reservaListNewReservaToAttach : reservaListNew) {
                reservaListNewReservaToAttach = em.getReference(reservaListNewReservaToAttach.getClass(), reservaListNewReservaToAttach.getIdre());
                attachedReservaListNew.add(reservaListNewReservaToAttach);
            }
            reservaListNew = attachedReservaListNew;
            corridas.setReservaList(reservaListNew);
            corridas = em.merge(corridas);
            if (autobusOld != null && !autobusOld.equals(autobusNew)) {
                autobusOld.getCorridasList().remove(corridas);
                autobusOld = em.merge(autobusOld);
            }
            if (autobusNew != null && !autobusNew.equals(autobusOld)) {
                autobusNew.getCorridasList().add(corridas);
                autobusNew = em.merge(autobusNew);
            }
            if (destinoOld != null && !destinoOld.equals(destinoNew)) {
                destinoOld.getCorridasList().remove(corridas);
                destinoOld = em.merge(destinoOld);
            }
            if (destinoNew != null && !destinoNew.equals(destinoOld)) {
                destinoNew.getCorridasList().add(corridas);
                destinoNew = em.merge(destinoNew);
            }
            if (origenOld != null && !origenOld.equals(origenNew)) {
                origenOld.getCorridasList().remove(corridas);
                origenOld = em.merge(origenOld);
            }
            if (origenNew != null && !origenNew.equals(origenOld)) {
                origenNew.getCorridasList().add(corridas);
                origenNew = em.merge(origenNew);
            }
            for (Reserva reservaListOldReserva : reservaListOld) {
                if (!reservaListNew.contains(reservaListOldReserva)) {
                    reservaListOldReserva.setIdcorrida(null);
                    reservaListOldReserva = em.merge(reservaListOldReserva);
                }
            }
            for (Reserva reservaListNewReserva : reservaListNew) {
                if (!reservaListOld.contains(reservaListNewReserva)) {
                    Corridas oldIdcorridaOfReservaListNewReserva = reservaListNewReserva.getIdcorrida();
                    reservaListNewReserva.setIdcorrida(corridas);
                    reservaListNewReserva = em.merge(reservaListNewReserva);
                    if (oldIdcorridaOfReservaListNewReserva != null && !oldIdcorridaOfReservaListNewReserva.equals(corridas)) {
                        oldIdcorridaOfReservaListNewReserva.getReservaList().remove(reservaListNewReserva);
                        oldIdcorridaOfReservaListNewReserva = em.merge(oldIdcorridaOfReservaListNewReserva);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = corridas.getIdcorrida();
                if (findCorridas(id) == null) {
                    throw new NonexistentEntityException("The corridas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Corridas corridas;
            try {
                corridas = em.getReference(Corridas.class, id);
                corridas.getIdcorrida();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The corridas with id " + id + " no longer exists.", enfe);
            }
            Autobus autobus = corridas.getAutobus();
            if (autobus != null) {
                autobus.getCorridasList().remove(corridas);
                autobus = em.merge(autobus);
            }
            Poblacion destino = corridas.getDestino();
            if (destino != null) {
                destino.getCorridasList().remove(corridas);
                destino = em.merge(destino);
            }
            Poblacion origen = corridas.getOrigen();
            if (origen != null) {
                origen.getCorridasList().remove(corridas);
                origen = em.merge(origen);
            }
            List<Reserva> reservaList = corridas.getReservaList();
            for (Reserva reservaListReserva : reservaList) {
                reservaListReserva.setIdcorrida(null);
                reservaListReserva = em.merge(reservaListReserva);
            }
            em.remove(corridas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Corridas> findCorridasEntities() {
        return findCorridasEntities(true, -1, -1);
    }

    public List<Corridas> findCorridasEntities(int maxResults, int firstResult) {
        return findCorridasEntities(false, maxResults, firstResult);
    }

    private List<Corridas> findCorridasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Corridas.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Corridas findCorridas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Corridas.class, id);
        } finally {
            em.close();
        }
    }

    public int getCorridasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Corridas> rt = cq.from(Corridas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
