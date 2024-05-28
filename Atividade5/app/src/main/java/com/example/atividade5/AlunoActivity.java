package com.example.atividade5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atividade5.R;
import com.example.atividade5.api.ApiAlunos;
import com.example.atividade5.api.AlunoService;
import com.example.atividade5.model.aluno;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.Response.Listener;
import static com.android.volley.Response.ErrorListener;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AlunoActivity extends AppCompatActivity {

    Button btnSalvar, btnListagem;
    EditText txtCep, txtLogadouro, txtCidade, txtUf, txtBairro, txtNome, txtRa, txtComplemento;
    AlunoService apiService;
    RequestQueue requestQueue;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);

        txtCep = findViewById(R.id.txtCep);
        txtLogadouro = findViewById(R.id.txtLogadouro);
        txtCidade = findViewById(R.id.txtCidade);
        txtUf = findViewById(R.id.txtUf);
        txtBairro = findViewById(R.id.txtBairro);


        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        apiService = ApiAlunos.getUsuarioService();
        txtNome = findViewById(R.id.txtNomeAluno);
        txtComplemento = findViewById(R.id.txtComplemento);
        txtRa = findViewById(R.id.txtRa);

        requestQueue = Volley.newRequestQueue(this);



        id = getIntent().getIntExtra("id", 0);
        if (id > 0) {
            apiService.getalunoPorId(id).enqueue(new Callback<aluno>() {
                @Override
                public void onResponse(Call<aluno> call, retrofit2.Response<aluno> response) {
                    if (response.isSuccessful()) {
                        txtNome.setText(response.body().getNome());
                        txtRa.setText(response.body().getRa());
                        txtComplemento.setText(response.body().getComplemento());
                        txtBairro.setText(response.body().getBairro());
                        txtCep.setText(response.body().getCep());
                        txtCidade.setText(response.body().getCidade());
                        txtLogadouro.setText(response.body().getLogadouro());
                        txtUf.setText(response.body().getUf());


                    }
                }

                @Override
                public void onFailure(Call<aluno> call, Throwable t) {
                    Log.e("Obter usuario", "Erro ao obter usuario");
                }
            });
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aluno aluno = new aluno();
                aluno.setComplemento(txtComplemento.getText().toString());
                aluno.setNome(txtNome.getText().toString());
                aluno.setBairro(txtBairro.getText().toString());
                aluno.setCidade(txtCidade.getText().toString());
                aluno.setCep(txtCep.getText().toString());
                aluno.setLogadouro(txtLogadouro.getText().toString());
                aluno.setUf(txtUf.getText().toString());
                aluno.setRa(Integer.parseInt(txtRa.getText().toString()));

                if (id == 0)
                    inserirUsuario(aluno);
                else {
                    aluno.setId(id);
                    editarUsuario(aluno);
                }
            }

            private void inserirUsuario(aluno aluno) {
                Call<aluno> call = apiService.postaluno(aluno);
                call.enqueue(new Callback<aluno>() {
                    @Override
                    public void onResponse(Call<aluno> call, retrofit2.Response<aluno> response) {
                        if (response.isSuccessful()) {
// A solicitação foi bem-sucedida
                            aluno createdPost = response.body();
                            Toast.makeText(AlunoActivity.this, "Inserido com sucesso!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
// A solicitação falhou
                            Log.e("Inserir", "Erro ao criar: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<aluno> call, Throwable t) {
// Ocorreu um erro ao fazer a solicitação
                        Log.e("Inserir", "Erro ao criar: " + t.getMessage());
                    }


                });
            }

            private void editarUsuario(aluno usuario) {
                Call<aluno> call = apiService.putaluno(id, usuario);
                call.enqueue(new Callback<aluno>() {
                    @Override
                    public void onResponse(Call<aluno> call, Response<aluno> response) {
                        if (response.isSuccessful()) {
// A solicitação foi bem-sucedida
                            aluno createdPost = response.body();
                            Toast.makeText(AlunoActivity.this, "Editado com sucesso!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
// A solicitação falhou
                            Log.e("Editar", "Erro ao editar: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<aluno> call, Throwable t) {
// Ocorreu um erro ao fazer a solicitação
                        Log.e("Editar", "Erro ao editar: " + t.getMessage());
                    }
                });
            }
        });
        txtCep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Não é necessário implementar
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Não é necessário implementar
            }

            @Override
            public void afterTextChanged(Editable s) {
                String cep = s.toString().trim();
                if (cep.length() == 8) {  // Assumindo que o CEP brasileiro tem 8 dígitos
                    buscarCep(cep);
                }
            }
        });
    }

    private void buscarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("erro")) {
                                Toast.makeText(AlunoActivity.this, "CEP não encontrado.", Toast.LENGTH_SHORT).show();
                            } else {
                                String cidade = response.getString("localidade");
                                String estado = response.getString("uf");
                                String bairro = response.getString("bairro");
                                String rua = response.getString("logradouro");

                                txtCidade.setText(cidade);
                                txtUf.setText(estado);
                                txtBairro.setText(bairro);
                                txtLogadouro.setText(rua);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AlunoActivity.this, "Erro ao processar os dados.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(AlunoActivity.this, "Erro na requisição.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}


