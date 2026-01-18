package com.pro.model;

public class User {
	private Integer id;
	private String nome;

	public User(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
}