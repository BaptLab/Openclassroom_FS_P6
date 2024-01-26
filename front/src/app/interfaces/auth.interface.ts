export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  email: string;
  username: string;
  password: string;
}

export interface AuthSuccess {
  token: string;
  username: string;
  email: string;
  id: number;
}
export interface ReturnedMessage {
  message: string;
}
