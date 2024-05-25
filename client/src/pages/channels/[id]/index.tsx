import { useChannelApi } from "@/api/channel";
import { Channel } from "@/types/channel";
import { Message } from "@/types/message";
import {
	Stack,
	Text,
	Paper,
	Textarea,
	Flex,
	ActionIcon,
	Divider,
	ScrollArea,
	Group,
	ThemeIcon,
	Avatar,
} from "@mantine/core";
import { mdiBullhorn, mdiSend } from "@mdi/js";
import Icon from "@mdi/react";
import { useEffect, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import CryptoJS from "crypto-js";
import { useAuthStore } from "@/stores/auth";
import React from "react";
import { useForm } from "@mantine/form";
import MarkdownRender from "@/components/utils/MarkdownRender";
import dayjs from "dayjs";

export default function Page() {
	const channelApi = useChannelApi();
	const authStore = useAuthStore();

	const { id } = useParams<{ id: string }>();

	const [intervalID, setIntervalID] = useState<NodeJS.Timeout>();
	const [channel, setChannel] = useState<Channel>();
	const [messages, setMessages] = useState<Array<Message>>([]);

	const viewport = useRef<HTMLDivElement>(null);

	const scrollToBottom = () =>
		viewport.current!.scrollTo({
			top: viewport.current!.scrollHeight,
			behavior: "smooth",
		});

	const form = useForm({
		mode: "controlled",
		initialValues: {
			content: "",
		},
		validate: {
			content: (value) => {
				if (value === "") {
					return true;
				}
				return null;
			},
		},
	});

	function getChannel() {
		channelApi.getChannel(Number(id)).then((res) => {
			const r = res.data;
			setChannel(r?.data);
		});
	}

	function getMessages() {
		channelApi.getMessages(Number(id)).then((res) => {
			const r = res.data;
			setMessages(r?.data?.reverse() || []);
		});
	}

	function sendMessage() {
		channelApi
			.sendMessage(
				Number(id),
				Number(authStore?.user?.id),
				form.getValues().content
			)
			.then((_) => {
				scrollToBottom();
			})
			.finally(() => {
				form.reset();
				getMessages();
			});
	}

	useEffect(() => {
		if (intervalID) {
			clearInterval(intervalID);
		}
		const newIntervalID = setInterval(() => {
			getMessages();
			scrollToBottom();
		}, 1000);
		setIntervalID(newIntervalID);
		return () => {
			clearInterval(newIntervalID);
		};
	}, [id]);

	useEffect(() => {
		getChannel();
		getMessages();
	}, [id]);

	useEffect(() => {
		document.title = `${channel?.name} - Edge`;
	}, [channel]);

	return (
		<>
			<Stack align={"center"} justify={"center"} my={50}>
				<Paper
					mih={"75vh"}
					w={"60%"}
					shadow={"xl"}
					p={0}
					withBorder
					radius={"md"}
				>
					<Stack justify={"space-between"} mih={"75vh"} p={20}>
						<Stack m={5}>
							<Group>
								<ThemeIcon variant={"transparent"}>
									<Icon path={mdiBullhorn} />
								</ThemeIcon>
								<Text fw={600} size="1.25rem">
									{channel?.name}
								</Text>
							</Group>
							<Divider my={10} />
						</Stack>
						<Stack px={20}>
							<ScrollArea
								h={"calc(42vh)"}
								offsetScrollbars
								viewportRef={viewport}
							>
								<Stack mx={20}>
									{messages?.map((message) => (
										<React.Fragment key={message?.id}>
											<Flex
												justify={
													message?.user?.id ===
													authStore?.user?.id
														? "end"
														: "start"
												}
												gap={10}
												direction={
													message?.user?.id ===
													authStore?.user?.id
														? "row"
														: "row-reverse"
												}
											>
												<Stack
													gap={5}
													align={
														message?.user?.id ===
														authStore?.user?.id
															? "end"
															: "start"
													}
												>
													<Flex
														gap={10}
														direction={
															message?.user
																?.id ===
															authStore?.user?.id
																? "row-reverse"
																: "row"
														}
													>
														<Text
															size="0.9rem"
															fw={600}
														>
															{
																message?.user
																	?.nickname
															}
														</Text>
														<Text size="0.75rem">
															{dayjs(
																message?.created_at
															).format(
																"YYYY-MM-DD HH:mm:ss"
															)}
														</Text>
													</Flex>
													<Paper
														maw={450}
														p={10}
														bg={"#FFF"}
														shadow="xs"
													>
														<MarkdownRender
															src={String(
																message?.content
															)}
														/>
													</Paper>
												</Stack>
												<Avatar
													src={`https://cravatar.cn/avatar/${CryptoJS.MD5(String(message?.user?.email))}`}
													size={40}
												/>
											</Flex>
										</React.Fragment>
									))}
								</Stack>
							</ScrollArea>
						</Stack>
						<form onSubmit={form.onSubmit(() => sendMessage())}>
							<Flex align={"end"} gap={20}>
								<Textarea
									variant="filled"
									label="请输入内容"
									description="支持 Markdown 语法"
									minRows={4}
									maxRows={4}
									placeholder={"说点什么..."}
									autosize
									resize={"vertical"}
									sx={{
										flexGrow: 1,
									}}
									key={form.key("content")}
									{...form.getInputProps("content")}
								></Textarea>
								<ActionIcon
									variant={"transparent"}
									type={"submit"}
								>
									<Icon path={mdiSend} />
								</ActionIcon>
							</Flex>
						</form>
					</Stack>
				</Paper>
			</Stack>
		</>
	);
}
