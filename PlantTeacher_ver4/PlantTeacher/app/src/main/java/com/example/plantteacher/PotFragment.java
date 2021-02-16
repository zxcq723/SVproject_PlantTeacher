package com.example.plantteacher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;

public class PotFragment extends Fragment implements onBackPressedListener{
    MainActivity mainActivity;
    ImageView product1;
    TextView product1_name;
    Button back;
    Bundle bundle;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) getLayoutInflater().inflate(R.layout.pot_fragment, container, false);
        product1 = (ImageView) rootView.findViewById(R.id.product1);
        product1_name = (TextView) rootView.findViewById(R.id.product1_name);
        product1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();

                Drawable d = product1.getDrawable();
                Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bitMapData = stream.toByteArray();
                bundle.putByteArray("image",bitMapData);

                String name = product1_name.getText().toString();
                bundle.putString("name",name);

                bundle.putInt("back",9);
                mainActivity.onChangedFragment(10,bundle);
            }
        });

        back = (Button) rootView.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.onChangedFragment(7,null);
            }
        });
        return rootView;
    }

    @Override
    public void onBackPressed() {
        mainActivity.onChangedFragment(7,null);
    }
}
