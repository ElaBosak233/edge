import { UserRegisterRequest } from "@/types/user";
import { useApi, useAuth } from "@/utils/axios";

export function useUserApi() {
	const api = useApi();

	const login = (username: string, password: string) => {
		return api.post("/users/login", {
			username,
			password,
		});
	};

	const register = (request: UserRegisterRequest) => {
		return api.post("/users/register", {
			username: request.username,
			nickname: request.nickname,
			password: request.password,
			email: request.email,
		});
	};

	return {
		login,
		register,
	};
}
