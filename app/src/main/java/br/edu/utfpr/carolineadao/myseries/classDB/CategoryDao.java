package br.edu.utfpr.carolineadao.myseries.classDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.edu.utfpr.carolineadao.myseries.models.Category;

@Dao
public interface CategoryDao {

    @Insert
    long insert(Category tipo);

    @Delete
    void delete(Category tipo);

    @Update
    void update(Category tipo);

    @Query("SELECT * FROM categories WHERE id = :id")
    Category queryForId(long id);

    @Query("SELECT * FROM categories ORDER BY name ASC")
    List<Category> queryAll();

    @Query("SELECT * FROM categories WHERE UPPER(name) = UPPER(:name) ORDER BY name ASC")
    List<Category> queryForName(String name);

    @Query("SELECT count(*) FROM categories")
    int total();
}
