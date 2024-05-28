package com.example.atividade5.api;

import com.example.atividade5.model.aluno;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AlunoService {

    @GET("aluno")
    Call<List<aluno>> getaluno();
    @POST("aluno")
    Call<aluno> postaluno(@Body aluno aluno);
    @DELETE("aluno/{id}")
    Call<Void> deletealuno(@Path("id") int idaluno);
    @GET("aluno/{id}")
    Call<aluno> getalunoPorId(@Path("id") int idaluno);
    @PUT("aluno/{id}")
    Call<aluno> putaluno(@Path("id") int idUsuario, @Body aluno aluno);
}











