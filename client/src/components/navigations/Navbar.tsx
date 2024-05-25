import {
	Avatar,
	BackgroundImage,
	Badge,
	Box,
	Button,
	Flex,
	Group,
	Menu,
	Title,
	Text,
	ThemeIcon,
} from "@mantine/core";
import { useNavigate } from "react-router-dom";
import { Channel } from "@/types/channel";
import { useEffect, useState } from "react";
import { useChannelApi } from "@/api/channel";
import { useAuthStore } from "@/stores/auth";
import CryptoJS from "crypto-js";
import MDIcon from "@/components/ui/MDIcon";

export default function Navbar() {
	const channelApi = useChannelApi();
	const authStore = useAuthStore();
	const navigate = useNavigate();

	const [channels, setChannels] = useState<Array<Channel>>([]);

	function getChannels() {
		channelApi.getChannels().then((res) => {
			const r = res.data;
			setChannels(r.data);
		});
	}

	function logout() {
		authStore.setPgsToken("");
		authStore.setUser(undefined);
		navigate("/login");
	}

	useEffect(() => {
		getChannels();
	}, []);

	return (
		<Flex
			h={64}
			w={"100%"}
			bg={"brand"}
			px={20}
			display={"flex"}
			justify={"space-between"}
			align={"center"}
			pos={"fixed"}
			sx={{
				top: 0,
				zIndex: 2,
			}}
		>
			<Box
				w={"50%"}
				sx={{
					display: "flex",
					justifyContent: "start",
				}}
			>
				<Button
					h={48}
					sx={{
						backgroundColor: "transparent",
						"&:hover": {
							backgroundColor: "transparent",
						},
					}}
					onClick={() => navigate("/")}
				>
					<Flex align={"center"}>
						<BackgroundImage src={"/favicon.png"} w={36} h={36} />
						<Title
							px={10}
							order={3}
							sx={{
								color: "white",
							}}
						>
							Edge
						</Title>
					</Flex>
				</Button>
			</Box>
			<Box
				sx={{
					flexShrink: 0,
				}}
			>
				{channels?.map((channel) => (
					<Button
						key={channel?.id}
						sx={{
							backgroundColor: "transparent",
							"&:hover": {
								backgroundColor: "transparent",
							},
						}}
						leftSection={
							<Badge
								variant="gradient"
								gradient={{ from: "blue", to: "cyan", deg: 40 }}
							>
								{channel?.id}
							</Badge>
						}
						onClick={() => navigate(`/channels/${channel?.id}`)}
					>
						{channel?.name}
					</Button>
				))}
			</Box>
			<Flex w={"50%"} justify={"end"} align={"center"}>
				<Group gap={10}>
					{!authStore?.user && (
						<Avatar
							src={`https://cravatar.cn/avatar/${CryptoJS.MD5(String("admin@admin.com"))}`}
							color="white"
							sx={{
								"&:hover": {
									cursor: "pointer",
								},
							}}
							onClick={() => navigate("/login")}
						/>
					)}
					{authStore?.user && (
						<Menu shadow="md" width={200} offset={20} withArrow>
							<Menu.Target>
								<Avatar
									src={`https://cravatar.cn/avatar/${CryptoJS.MD5(String(authStore?.user?.email))}`}
									color="white"
									sx={{
										"&:hover": {
											cursor: "pointer",
										},
									}}
								/>
							</Menu.Target>
							<Menu.Dropdown>
								<Group c={"brand"} p={10}>
									<ThemeIcon variant="transparent">
										<MDIcon>person</MDIcon>
									</ThemeIcon>
									<Text fw={600}>
										{authStore?.user?.nickname}
									</Text>
								</Group>
								<Menu.Divider />
								<Menu.Item
									c={"red"}
									leftSection={
										<ThemeIcon
											variant="transparent"
											color="red"
										>
											<MDIcon>logout</MDIcon>
										</ThemeIcon>
									}
									onClick={logout}
								>
									退出
								</Menu.Item>
							</Menu.Dropdown>
						</Menu>
					)}
				</Group>
			</Flex>
		</Flex>
	);
}
