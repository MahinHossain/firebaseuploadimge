package com.example.firebaseuploadimge;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Activity contex;
    List<Student> studentList;

    public CustomAdapter(Activity contex, List<Student> studentList) {
        this.contex = contex;
        this.studentList = studentList;
    }

    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        LayoutInflater layoutinflater = LayoutInflater.from(contex);

        View view = layoutinflater.inflate(R.layout.sample_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolderholder, int position) {
        Student student = studentList.get(position);

        myViewHolderholder.t1.setText("Name: " + student.getName());
        myViewHolderholder.t2.setText("Age: " + student.getAge());
        myViewHolderholder.t3.setText("descrip: " + student.getDescrip());

        Picasso.with(contex).load(student.getImageUri()).fit().centerCrop().into(myViewHolderholder.imV);


    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }





    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t1, t2, t3;
        ImageView imV;

        public MyViewHolder(View view) {
            super(view);


            t1 = view.findViewById(R.id.nameidtv);
            t2 = view.findViewById(R.id.ageidtv);
            t3 = view.findViewById(R.id.desccripIdd);
            imV = view.findViewById(R.id.imageViewId);
        }
    }
}
