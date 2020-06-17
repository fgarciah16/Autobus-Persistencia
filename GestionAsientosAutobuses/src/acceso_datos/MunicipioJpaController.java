/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acceso_datos;

import acceso_datos.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Poblacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Municipio;

/**
 *
 * @author jesus
 */
public class MunicipioJpaController implements Serializable {

    public MunicipioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Municipio municipio) {
        if (municipio.getPoblacionList() == null) {
            municipio.setPoblacionList(new ArrayList<Poblacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Poblacion> attachedPoblacionList = new ArrayList<Poblacion>();
            for (Poblacion poblacionListPoblacionToAttach : municipio.getPoblacionList()) {
                poblacionListPoblacionToAttach = em.getReference(poblacionListPoblacionToAttach.getClass(), poblacionListPoblacionToAttach.getIdpob());
                attachedPoblacionList.add(poblacionListPoblacionToAttach);
            }
            municipio.setPoblacionList(attachedPoblacionList);
            em.persist(municipio);
            for (Poblacion poblacionListPoblacion : municipio.getPoblacionList()) {
                Municipio oldIdmpoOfPoblacionListPoblacion = poblacionListPoblacion.getIdmpo();
                poblacionListPoblacion.setIdmpo(municipio);
                poblacionListPoblacion = em.merge(poblacionListPoblacion);
                if (oldIdmpoOfPoblacionListPoblacion != null) {
                    oldIdmpoOfPoblacionListPoblacion.getPoblacionList().remove(poblacionListPoblacion);
                    oldIdmpoOfPoblacionListPoblacion = em.merge(oldIdmpoOfPoblacionListPoblacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Municipio municipio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipio persistentMunicipio = em.find(Municipio.class, municipio.getIdmpo());
            List<Poblacion> poblacionListOld = persistentMunicipio.getPoblacionList();
            List<Poblacion> poblacionListNew = municipio.getPoblacionList();
            List<Poblacion> attachedPoblacionListNew = new ArrayList<Poblacion>();
            for (Poblacion poblacionListNewPoblacionToAttach : poblacionListNew) {
                poblacionListNewPoblacionToAttach = em.getReference(poblacionListNewPoblacionToAttach.getClass(), poblacionListNewPoblacionToAttach.getIdpob());
                attachedPoblacionListNew.add(poblacionListNewPoblacionToAttach);
            }
            poblacionListNew = attachedPoblacionListNew;
            municipio.setPoblacionList(poblacionListNew);
            municipio = em.merge(municipio);
            for (Poblacion poblacionListOldPoblacion : poblacionListOld) {
                if (!poblacionListNew.contains(poblacionListOldPoblacion)) {
                    poblacionListOldPoblacion.setIdmpo(null);
                    poblacionListOldPoblacion = em.merge(poblacionListOldPoblacion);
                }
            }
            for (Poblacion poblacionListNewPoblacion : poblacionListNew) {
                if (!poblacionListOld.contains(poblacionListNewPoblacion)) {
                    Municipio oldIdmpoOfPoblacionListNewPoblacion = poblacionListNewPoblacion.getIdmpo();
                    poblacionListNewPoblacion.setIdmpo(municipio);
                    poblacionListNewPoblacion = em.merge(poblacionListNewPoblacion);
                    if (oldIdmpoOfPoblacionListNewPoblacion != null && !oldIdmpoOfPoblacionListNewPoblacion.equals(municipio)) {
                        oldIdmpoOfPoblacionListNewPoblacion.getPoblacionList().remove(poblacionListNewPoblacion);
                        oldIdmpoOfPoblacionListNewPoblacion = em.merge(oldIdmpoOfPoblacionListNewPoblacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = municipio.getIdmpo();
                if (findMunicipio(id) == null) {
                    throw new NonexistentEntityException("The municipio with id " + id + " no longer exists.");
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
            Municipio municipio;
            try {
                municipio = em.getReference(Municipio.class, id);
                municipio.getIdmpo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The municipio with id " + id + " no longer exists.", enfe);
            }
            List<Poblacion> poblacionList = municipio.getPoblacionList();
            for (Poblacion poblacionListPoblacion : poblacionList) {
                poblacionListPoblacion.setIdmpo(null);
                poblacionListPoblacion = em.merge(poblacionListPoblacion);
            }
            em.remove(municipio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Municipio> findMunicipioEntities() {
        return findMunicipioEntities(true, -1, -1);
    }

    public List<Municipio> findMunicipioEntities(int maxResults, int firstResult) {
        return findMunicipioEntities(false, maxResults, firstResult);
    }

    private List<Municipio> findMunicipioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Municipio.class));
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

    public Municipio findMunicipio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Municipio.class, id);
        } finally {
            em.close();
        }
    }

    public int getMunicipioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Municipio> rt = cq.from(Municipio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
