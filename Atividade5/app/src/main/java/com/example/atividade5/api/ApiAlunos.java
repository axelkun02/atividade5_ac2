package com.example.atividade5.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAlunos {

    private static final String BASE_URL = "https://665517843c1d3b60293835ca.mockapi.io/";
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static AlunoService getUsuarioService() {
        return getClient().create(AlunoService.class);
    }




}
