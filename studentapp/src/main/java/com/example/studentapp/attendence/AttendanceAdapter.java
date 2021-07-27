package com.example.studentapp.attendence;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentapp.R;
import com.example.studentapp.ebook.EbookData;
import com.example.studentapp.ebook.PdfViewer;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<com.example.studentapp.attendence.AttendanceAdapter.AttendanceViewHolder>{

    private Context context;
    private List<EbookData> list;

    public AttendanceAdapter(Context context, List<EbookData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public com.example.studentapp.attendence.AttendanceAdapter.AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.attendance_item,parent,false);
        return  new com.example.studentapp.attendence.AttendanceAdapter.AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.studentapp.attendence.AttendanceAdapter.AttendanceViewHolder holder, final int position) {
        EbookData data=list.get(position);
        holder.attendanceName.setText(data.getPdfTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, PdfViewer.class);
                i.putExtra("pdfUrl",list.get(position).getPdfUrl());
                context.startActivity(i);
            }
        });
        holder.attendanceDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData((Uri.parse(list.get(position).getPdfUrl())));
                context.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AttendanceViewHolder extends RecyclerView.ViewHolder {
        public TextView attendanceName;
        public ImageView attendanceDownload;

        public AttendanceViewHolder(@NonNull  View itemView) {
            super(itemView);
            attendanceName=itemView.findViewById(R.id.attendaneName);
            attendanceDownload=itemView.findViewById(R.id.attendanceDownload);
        }
    }

}
