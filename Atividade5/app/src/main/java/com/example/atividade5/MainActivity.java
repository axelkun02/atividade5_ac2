package com.example.atividade5;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atividade5.R;
import com.example.atividade5.adapter.AlunoAdapter;
import com.example.atividade5.api.ApiAlunos;
import com.example.atividade5.api.AlunoService;
import com.example.atividade5.model.aluno;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerUsuario;
    AlunoAdapter alunoAdapter;
    AlunoService apiService;
    List<aluno> listaAlunos;
    FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerUsuario = (RecyclerView) findViewById(R.id.recyclerUsuario);
        btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        apiService = ApiAlunos.getUsuarioService();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AlunoActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        obterUsuarios();
    }

    private void configurarRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerUsuario.setLayoutManager(layoutManager);
        alunoAdapter = new AlunoAdapter(listaAlunos, this);
        recyclerUsuario.setAdapter(alunoAdapter);
        recyclerUsuario.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void obterUsuarios() {
        retrofit2.Call<List<aluno>> call = apiService.getaluno();
        call.enqueue(new Callback<List<aluno>>() {
            @Override
            public void onResponse(Call<List<aluno>> call, Response<List<aluno>>
                    response) {
                listaAlunos = response.body();
                configurarRecycler();
            }

            @Override
            public void onFailure(Call<List<aluno>> call, Throwable t) {
                Log.e("TESTE", "Erro ao obter os contatos: " + t.getMessage());
            }
        });
    }
}