package com.example.plantteacher;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class DetectFragment extends Fragment {
    MainActivity mainActivity;

    Bitmap mBitmap;
    int CameraRequestCode = 0;
    int GalleryRequestCode = 2;

    Button photo;
    Button gallery;
    ImageView imageView;
    Button detect;
    TextView result;
    File file;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity =null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) getLayoutInflater().inflate(R.layout.detect_fragment, container, false);

        file = new File(Environment.getExternalStorageDirectory(),"capture.jpg");
        imageView = (ImageView) rootView.findViewById(R.id.imageView);


        photo = (Button) rootView.findViewById(R.id.photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(cameraIntent, CameraRequestCode);
            }
        });

        gallery = (Button) rootView.findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call = new Intent();
                call.setType("image/*");
                call.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(call, GalleryRequestCode);
            }
        });

        detect = (Button) rootView.findViewById(R.id.detect);
        result = (TextView) rootView.findViewById(R.id.result);
        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GalleryRequestCode)
        {
            if(resultCode == mainActivity.RESULT_OK)
            {
                try{
                    InputStream in = mainActivity.getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    mBitmap = img;
                    in.close();

                    imageView.setImageBitmap(img);
                }catch(Exception e)
                {

                }
            }else if(resultCode == mainActivity.RESULT_CANCELED)
            {
                Toast.makeText(getContext(), "cancelled", Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode == CameraRequestCode)
        {
            if(resultCode == mainActivity.RESULT_OK)
            {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),options);

                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
