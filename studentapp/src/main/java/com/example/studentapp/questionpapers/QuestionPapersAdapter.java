package com.example.studentapp.questionpapers;

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

public class QuestionPapersAdapter extends RecyclerView.Adapter<com.example.studentapp.questionpapers.QuestionPapersAdapter.QuestionPapersViewHolder>{
    private Context context;
    private List<EbookData> list;

    public QuestionPapersAdapter(Context context, List<EbookData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public com.example.studentapp.questionpapers.QuestionPapersAdapter.QuestionPapersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.question_papers_item,parent,false);
        return  new com.example.studentapp.questionpapers.QuestionPapersAdapter.QuestionPapersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.studentapp.questionpapers.QuestionPapersAdapter.QuestionPapersViewHolder holder, final int position) {
        EbookData data=list.get(position);
        holder.questionPapersName.setText(data.getPdfTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, PdfViewer.class);
                i.putExtra("pdfUrl",list.get(position).getPdfUrl());
                context.startActivity(i);
            }
        });
        holder.questionPapersDownload.setOnClickListener(new View.OnClickListener() {
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

    public class QuestionPapersViewHolder extends RecyclerView.ViewHolder {
        public TextView questionPapersName;
        public ImageView questionPapersDownload;

        public QuestionPapersViewHolder(@NonNull  View itemView) {
            super(itemView);
            questionPapersName=itemView.findViewById(R.id.questionPapersName);
            questionPapersDownload=itemView.findViewById(R.id.questionPapersDownload);
        }
    }

}

