package com.status.ki.duniya.sad.status.models;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

public class DownloadedModel {

    ArrayList<PictureItem> pictureItems;
    Photo photo = Photo.getInstance();

    public ArrayList<PictureItem> getData(){

        //Target folder
        pictureItems = new ArrayList<PictureItem>();

        PictureItem p;

        if (photo.imagedir.exists()) {

            //get all images from that folder
            File[] files = photo.imagedir.listFiles();

            //loop through those files get image uri and date
            if (files != null) {
                for (int i = 0; i <= files.length - 1; i++) {

                    File file = files[i];
                    p = new PictureItem();
                    p.setUri(Uri.fromFile(file));
                    int position = i;
                    p.setPosition(position);
                    p.setName(file.getName());

                    pictureItems.add(p);
                }
            }
        }else{
            photo.imagedir.mkdirs();
        }

        return pictureItems;
    }

    public boolean IsAlreadyDownloaded(String filename){

        PictureItem p;

        pictureItems = getData();

        for (int i=0; i<pictureItems.size(); i++){

            p = pictureItems.get(i);
            if (p.getName().equalsIgnoreCase(filename)){
                return true;
            }
        }
        return false;
    }


}
