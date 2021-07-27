package com.example.studentapp.ebook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentapp.R;

import java.util.List;

public class EbookAdapter extends RecyclerView.Adapter<EbookAdapter.EbookViewHolder>{
    private Context context;
    private List<EbookData>list;

    public EbookAdapter(Context context, List<EbookData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public EbookViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
       View view=LayoutInflater.from(context).inflate(R.layout.ebook_item_layout,parent,false);
       return  new EbookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EbookViewHolder holder, final int position) {
        EbookData data=list.get(position);



        holder.ebookName.setText(data.getPdfTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,PdfViewer.class);
                i.putExtra("pdfUrl",list.get(position).getPdfUrl());
                context.startActivity(i);
            }
        });
        holder.ebooKDownload.setOnClickListener(new View.OnClickListener() {
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

    public class EbookViewHolder extends RecyclerView.ViewHolder {
       public TextView ebookName;
     public    ImageView ebooKDownload;

        public EbookViewHolder(@NonNull  View itemView) {
            super(itemView);
            ebookName=itemView.findViewById(R.id.ebookName);
            ebooKDownload=itemView.findViewById(R.id.ebookDownload);
        }
    }

}
