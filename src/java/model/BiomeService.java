package model;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class BiomeService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public BiomeService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public boolean addBiome(Biome biome) {
        mgr.persist(biome);
        return true;
    }

    public Biome findById(String id) {
        return mgr.find(Biome.class, id);
    }

    public List<Biome> findByName(String name) {
        TypedQuery<Biome> query = mgr.createNamedQuery("Biome.findByBiomename", Biome.class);
        query.setParameter("biomename", name);
        return query.getResultList();
    }

    public boolean deleteBiome(String id) {
        Biome biome = findById(id);
        if (biome != null) {
            mgr.remove(biome);
            return true;
        }
        return false;
    }

    public List<Biome> findAll() {
        query = mgr.createNamedQuery("Biome.findAll");
        return query.getResultList();
    }

    public boolean updateBiome(Biome biome) {
        Biome existing = findById(biome.getBiomeid());
        if (existing != null) {
            existing.setBiomename(biome.getBiomename());
            existing.setBiomedescription(biome.getBiomedescription());
            existing.setBiomecost(biome.getBiomecost());
            existing.setIsdeleted(biome.getIsdeleted());
            existing.setIsarchived(biome.getIsarchived());
            existing.setBiomeimage(biome.getBiomeimage());
            return true;
        }
        return false;
    }
}
