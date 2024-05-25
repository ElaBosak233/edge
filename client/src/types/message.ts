import { Channel } from "./channel";
import { User } from "./user";

export interface Message {
	id?: number;
	content?: string;
	user?: User;
	channel?: Channel;
	user_id?: number;
	channel_id?: number;
	created_at?: string;
}
