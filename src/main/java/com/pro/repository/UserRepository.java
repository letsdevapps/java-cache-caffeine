package com.pro.repository;

import com.pro.model.User;

public class UserRepository {

	public User buscarPorId(Integer id) {
		simularLentidao();
		return new User(id, "Usuário " + id);
	}

	private void simularLentidao() {
		try {
			Thread.sleep(8000); // 8 segundos
		} catch (InterruptedException ignored) {
		}
	}
}