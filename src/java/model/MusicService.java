package model;

import java.util.ArrayList;
import javax.persistence.*;
import java.util.List;

public class MusicService {

    @PersistenceContext
    private EntityManager mgr;

    public MusicService(EntityManager mgr) {
        this.mgr = mgr;
    }

    // Add new music
    public boolean addMusic(Music music) {
        try {
            mgr.persist(music);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Find all music
    public List<Music> findAllMusic() {
        List<Music> musicList = mgr.createNamedQuery("Music.findAll", Music.class).getResultList();
        if (musicList == null) {
            return new ArrayList<>();  // Return an empty list if no results are found
        }
        return musicList;
    }

    // Find music by ID
    public Music findMusicById(String musicId) {
        return mgr.find(Music.class, musicId);
    }

    // Find music by name
    public List<Music> findMusicByName(String musicName) {
        TypedQuery<Music> query = mgr.createNamedQuery("Music.findByMusicname", Music.class);
        query.setParameter("musicname", musicName);
        return query.getResultList();
    }

    // Find music by author
    public List<Music> findMusicByAuthor(String author) {
        TypedQuery<Music> query = mgr.createNamedQuery("Music.findByAuthor", Music.class);
        query.setParameter("author", author);
        return query.getResultList();
    }

    // Delete music by ID
    public boolean deleteMusic(String musicId) {
        Music music = findMusicById(musicId);
        if (music != null) {
            mgr.remove(music);
            return true;
        }
        return false;
    }

    // Update music details
    public boolean updateMusic(Music music) {
        try {
            // Find the music to update
            Music existingMusic = mgr.find(Music.class, music.getMusicid());
            if (existingMusic != null) {
                existingMusic.setMusicname(music.getMusicname());
                existingMusic.setAuthor(music.getAuthor());
                existingMusic.setFilepath(music.getFilepath());
                existingMusic.setMhour(music.getMhour());
                existingMusic.setMminute(music.getMminute());
                existingMusic.setMsecond(music.getMsecond());
                existingMusic.setIsdeleted(music.getIsdeleted());
                mgr.merge(existingMusic);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
