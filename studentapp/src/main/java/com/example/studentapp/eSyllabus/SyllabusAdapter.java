package com.example.studentapp.eSyllabus;

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

public class SyllabusAdapter extends RecyclerView.Adapter<SyllabusAdapter.SyllabusViewHolder>{
    private Context context;
    private List<EbookData> list;

    public SyllabusAdapter(Context context, List<EbookData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public SyllabusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.syllabus_item,parent,false);
        return  new SyllabusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SyllabusViewHolder holder, final int position) {
        EbookData data=list.get(position);
        holder.syllabusName.setText(data.getPdfTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, PdfViewer.class);
                i.putExtra("pdfUrl",list.get(position).getPdfUrl());
                context.startActivity(i);
            }
        });
        holder.syllabusDownload.setOnClickListener(new View.OnClickListener() {
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

    public class SyllabusViewHolder extends RecyclerView.ViewHolder {
        public TextView syllabusName;
        public ImageView syllabusDownload;

        public SyllabusViewHolder(@NonNull  View itemView) {
            super(itemView);
            syllabusName=itemView.findViewById(R.id.syllabusName);
            syllabusDownload=itemView.findViewById(R.id.syllabusDownload);
        }
    }

}
