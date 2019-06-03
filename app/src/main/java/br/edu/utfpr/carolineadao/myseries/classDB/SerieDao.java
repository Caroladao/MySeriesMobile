package br.edu.utfpr.carolineadao.myseries.classDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.edu.utfpr.carolineadao.myseries.models.Serie;

@Dao
public interface SerieDao {

    @Insert
    long insert(Serie pessoa);

    @Delete
    void delete(Serie pessoa);

    @Update
    void update(Serie pessoa);

    @Query("SELECT * FROM series WHERE id = :id")
    Serie queryForId(long id);

    @Query("SELECT * FROM series ORDER BY name ASC")
    List<Serie> queryAll();

    @Query("SELECT * FROM series WHERE categoryId = :id ORDER BY name ASC")
    List<Serie> queryForCategoryId(long id);
}