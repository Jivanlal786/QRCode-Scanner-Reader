package com.hello.status.ektarfa.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hello.status.ektarfa.entity.Like;

import java.util.List;

@Dao
public interface LikeDao {

    @Insert
    Long addData(Like like);

    @Query("SELECT * FROM like_table WHERE category like :category")
    public List<Like> getLikeCategory(String category);

    @Query("UPDATE like_table SET count = :count WHERE image like :image")
    int updatecount(String image, int count);

    @Query("UPDATE like_table SET count = :count, islike = :islike WHERE image like :image")
    int updatelikecount(String image, int count, boolean islike);

    /*@Update
    public void updateData(Like like);*/

  /*  @Query("SELECT * FROM like_table")
    public List<Like> getLikesData();*/

    @Query("SELECT * FROM like_table WHERE image like :image")
    public Like getdata(String image);

    /*@Query("SELECT * FROM like_table WHERE image like :image")
    public boolean isLike(String image);*/

    @Query("UPDATE like_table SET isfavorite = :favorite WHERE image like :image ")
    int updatefavorite(String image, Boolean favorite);

    /*@Query("SELECT * FROM like_table WHERE isfavorite =1")
    List<Like> getFavorites();*/

   /* @Query("SELECT isfavorite FROM like_table WHERE image like :image ")
    boolean checkfavorite(String image);*/

    //http://jrdreams.in/quotes_latest/aapki_taarife/a%20(2).jpg
    //aapki_taarife_2.jpg


}
