export interface User {
	id?: number;
	username?: string;
	nickname?: string;
	email?: string;
}

export interface UserRegisterRequest {
	username: string;
	nickname: string;
	password: string;
	email: string;
}
