package com.example.atividade5.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atividade5.R;
import com.example.atividade5.AlunoActivity;
import com.example.atividade5.api.ApiAlunos;
import com.example.atividade5.api.AlunoService;
import com.example.atividade5.model.aluno;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.AlunoHolder> {

    private final List<aluno> alunos;
    Context context;

    public AlunoAdapter(List<aluno> usuarios, Context context) {
        this.alunos = usuarios;
        this.context = context;
    }

    @Override
    public AlunoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlunoHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_aluno, parent, false));
    }



    @Override
    public void onBindViewHolder(AlunoHolder holder, int position) {
        holder.nome.setText(alunos.get(position).getId() + " - " + alunos.get(position).getNome());
        holder.ra.setText(alunos.get(position).getRa());
        holder.btnexcluir.setOnClickListener(view -> removerItem(position));
        holder.btnEditar.setOnClickListener(view -> editarItem(position));
    }

    @Override
    public int getItemCount() {
        return alunos != null ? alunos.size() : 0;
    }

    public class AlunoHolder extends RecyclerView.ViewHolder {
        public TextView nome, ra;

        public ImageView btnexcluir;
        public ImageView btnEditar;

        public AlunoHolder(View itemView) {
            super(itemView);
            nome = (TextView) itemView.findViewById(R.id.txtNome);
            ra = (TextView) itemView.findViewById(R.id.txtRa);
            btnexcluir = (ImageView) itemView.findViewById(R.id.btnExcluir);
            btnEditar = (ImageView) itemView.findViewById(R.id.btnEditar);
        }
    }

    private void removerItem(int position) {
        int id = alunos.get(position).getId();
        AlunoService apiService = ApiAlunos.getUsuarioService();
        Call<Void> call = apiService.deletealuno(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
// A solicitação foi bem-sucedida
                    alunos.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, alunos.size());
                    Toast.makeText(context, "Excluído com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
// A solicitação falhou
                    Log.e("Exclusao","Erro ao excluir");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
// Ocorreu um erro ao fazer a solicitação
                Log.e("Exclusao","Erro ao excluir");
            }
        });
    }

    private void editarItem(int position) {
        int id = alunos.get(position).getId();
        Intent i = new Intent(context, AlunoActivity.class);
        i.putExtra("id",id);
        context.startActivity(i);
    }
}

















