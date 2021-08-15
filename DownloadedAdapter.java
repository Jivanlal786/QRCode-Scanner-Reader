package com.status.ki.duniya.sad.status.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.status.ki.duniya.sad.status.R;
import com.status.ki.duniya.sad.status.activities.DeleteImageActivity;
import com.status.ki.duniya.sad.status.activities.DownloadedActivity;
import com.status.ki.duniya.sad.status.models.Photo;
import com.status.ki.duniya.sad.status.models.PictureItem;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;


public class DownloadedAdapter extends RecyclerView.Adapter<DownloadedAdapter.DownloadViewHolder> {

    Activity activity;
    ArrayList<PictureItem> pictureItems;
    Photo photo = Photo.getInstance();

    public DownloadedAdapter(Activity activity, ArrayList<PictureItem> pictureItems ) {
        this.activity = activity;
        this.pictureItems = pictureItems;
    }

    @NonNull
    @Override
    public DownloadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.custom_downloaded_layout,parent,false);

        return new DownloadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadViewHolder holder, int position) {

        PictureItem  p = pictureItems.get(position);

        String imageUrl = p.getUri().toString();

        Glide.with(activity)
                .load(imageUrl)
                .into(holder.downloadimageView);

        holder.downloadimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(activity, DeleteImageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("IMAGE",position);
                    intent.putExtra("URI",p.getUri().toString());

                    activity.startActivity(intent);
                }catch (Exception e){
                    Log.e("aaaaaaaa", "onClick: "+e.getMessage() );
                }

            }
        });

        holder.ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MaterialAlertDialogBuilder(activity)
                        .setTitle("Delete Image")
                        .setMessage("Do you really want to delete this image")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                deleteImage(position);
                                dialog.dismiss();
                                notifyDataSetChanged();

                            }
                        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                }).show();
            }
        });

        holder.ibSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(activity, ""+pictureItems.get(position).getName()+"\n"+pictureItems.get(position).getUri(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pictureItems.size();
    }

    public class DownloadViewHolder extends RecyclerView.ViewHolder {

        ImageView downloadimageView;
        ImageView ibDelete,ibSave;
        Photo photo;
        CardView downloadedCardview;
        FrameLayout frame,mainFrame;

        public DownloadViewHolder(@NonNull View itemView) {
            super(itemView);

            downloadimageView = itemView.findViewById(R.id.iv_custom_downloaded);
            ibDelete = itemView.findViewById(R.id.ib_delete);
            downloadedCardview = itemView.findViewById(R.id.downloaded_cardview);
            frame = itemView.findViewById(R.id.frame);
            mainFrame = itemView.findViewById(R.id.main_frame);
            ibSave = itemView.findViewById(R.id.ib_save);

            photo = Photo.getInstance();

            FrameLayout.LayoutParams f1 = (FrameLayout.LayoutParams) mainFrame.getLayoutParams();
            f1.height = (int) (photo.Width / 3.1);
            f1.width = (int) (photo.Width / 3.2);
            f1.setMargins(photo.Width * 8 / 720, photo.Width * 8 / 720,0,0);

            f1 = (FrameLayout.LayoutParams) downloadedCardview.getLayoutParams();
            downloadedCardview.setBackgroundResource(R.drawable.round_corner_image);

            f1 = (FrameLayout.LayoutParams) downloadimageView.getLayoutParams();
            f1.gravity = Gravity.CENTER;
            downloadimageView.setBackgroundResource(R.drawable.round_corner_image);

            f1 = (FrameLayout.LayoutParams) ibDelete.getLayoutParams();
            f1.height = photo.Height * 60 / 1280;
            f1.width = photo.Height * 60 / 1280;
        }
    }

    public void setList(ArrayList<PictureItem> pictureItems) {
        this.pictureItems = pictureItems;
    }

    private void deleteImage(int pos) {

        if (photo.dir.exists()) {

            File file = null;
            try {
                file = new File(new URI(pictureItems.get(pos).getUri().toString()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            if (file.exists()) {
                file.delete();
                pictureItems.remove(pictureItems.get(pos));

                if (pictureItems.size() == 0){

                    DownloadedActivity.adNoImages.setVisibility(View.VISIBLE);
                }else {
                    DownloadedActivity.adNoImages.setVisibility(View.GONE);
                }
                Toast.makeText(activity, "File deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "file not deleted", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(activity, "No Such Directory", Toast.LENGTH_SHORT).show();
        }
    }

}
