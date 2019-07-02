package com.homemade.dungeondroid;

/**
 * Created by joaosousa on 21/12/16.
 */
public class Player {
    private int id;
    private String Username;
    private String Password;
    private int Vitorias;
    private int Derrotas;

    public Player() {

    }

    public Player(int id, String Username, String Password, int Vitorias, int Derrotas)
    {
        this.id=id;
        this.Username=Username;
        this.Password=Password;
        this.Vitorias=Vitorias;
        this.Derrotas=Derrotas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getVitorias() {
        return Vitorias;
    }

    public void setVitorias(int vitorias) {
        Vitorias = vitorias;
    }

    public int getDerrotas() {
        return Derrotas;
    }

    public void setDerrotas(int derrotas) {
        Derrotas = derrotas;
    }

}
