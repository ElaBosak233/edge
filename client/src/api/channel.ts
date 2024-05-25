import { useApi, useAuth } from "@/utils/axios";

export function useChannelApi() {
	const api = useApi();
	const auth = useAuth();

	const getChannels = () => {
		return api.get("/channels/");
	};

	const getChannel = (id: number) => {
		return auth.get(`/channels/${id}`);
	};

	const getMessages = (channelID: number) => {
		return auth.get(`/channels/${channelID}/messages`);
	};

	const sendMessage = (
		channelID: number,
		userID: number,
		content: string
	) => {
		return auth.post(`/channels/${channelID}/messages`, {
			user_id: userID,
			channel_id: channelID,
			content,
		});
	};

	return {
		getChannels,
		getChannel,
		sendMessage,
		getMessages,
	};
}
