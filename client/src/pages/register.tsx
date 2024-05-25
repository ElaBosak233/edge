import { useUserApi } from "@/api/user";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useForm } from "@mantine/form";
import {
	showErrNotification,
	showSuccessNotification,
} from "@/utils/notification";
import { Box, TextInput, Button, ThemeIcon } from "@mantine/core";
import MDIcon from "@/components/ui/MDIcon";

export default function Page() {
	const navigate = useNavigate();
	const userApi = useUserApi();

	useEffect(() => {
		document.title = "注册 - Edge";
	}, []);

	const [loginLoading, setLoginLoading] = useState(false);

	const form = useForm({
		mode: "controlled",
		initialValues: {
			username: "",
			nickname: "",
			email: "",
			password: "",
		},

		validate: {
			username: (value) => {
				if (value === "") {
					return "用户名不能为空";
				}
				return null;
			},
			password: (value) => {
				if (value === "") {
					return "密码不能为空";
				}
				return null;
			},
			nickname: (value) => {
				if (value === "") {
					return "昵称不能为空";
				}
				return null;
			},
			email: (value) => {
				if (value === "") {
					return "邮箱不能为空";
				}
				return null;
			},
		},
	});

	function register() {
		setLoginLoading(true);
		userApi
			.register({
				username: form.getValues().username,
				nickname: form.getValues().nickname,
				email: form.getValues().email,
				password: form.getValues().password,
			})
			.then((_) => {
				showSuccessNotification({
					title: "注册成功",
					message: "请登录",
				});
				navigate("/login");
			})
			.catch((err) => {
				showErrNotification({
					message: "注册失败",
				});
			})
			.finally(() => {
				setLoginLoading(false);
			});
	}

	return (
		<>
			<Box
				sx={{
					position: "fixed",
					top: "50%",
					left: "50%",
					zIndex: -1,
					transform: "translate(-50%, -50%)",
				}}
				className={"no-select"}
			>
				<Box
					w={500}
					sx={{
						display: "flex",
						flexDirection: "column",
						marginTop: "2rem",
					}}
				>
					<form onSubmit={form.onSubmit(() => register())}>
						<TextInput
							label="用户名"
							description="用户名将会作为你的登录名"
							size="lg"
							leftSection={
								<ThemeIcon variant={"transparent"}>
									<MDIcon>person</MDIcon>
								</ThemeIcon>
							}
							key={form.key("username")}
							{...form.getInputProps("username")}
						/>
						<TextInput
							label="昵称"
							description="昵称将会作为你的聊天名"
							size="lg"
							leftSection={
								<ThemeIcon variant={"transparent"}>
									<MDIcon>person</MDIcon>
								</ThemeIcon>
							}
							key={form.key("nickname")}
							{...form.getInputProps("nickname")}
						/>
						<TextInput
							label="邮箱"
							description="邮箱将会作为你的找回密码的凭证"
							size="lg"
							leftSection={
								<ThemeIcon variant={"transparent"}>
									<MDIcon>email</MDIcon>
								</ThemeIcon>
							}
							key={form.key("email")}
							{...form.getInputProps("email")}
						/>
						<TextInput
							label="密码"
							type="password"
							size="lg"
							leftSection={
								<ThemeIcon variant={"transparent"}>
									<MDIcon>lock</MDIcon>
								</ThemeIcon>
							}
							mt={10}
							key={form.key("password")}
							{...form.getInputProps("password")}
						/>
						<Button
							loading={loginLoading}
							size={"lg"}
							fullWidth
							sx={{ marginTop: "2rem", bgcolor: "primary.700" }}
							type="submit"
						>
							注册
						</Button>
					</form>
					<Box
						sx={{
							display: "flex",
							marginTop: "1rem",
							justifyContent: "end",
						}}
					>
						已有帐号？
						<Box
							onClick={() => navigate("/login")}
							sx={{
								fontStyle: "italic",
								":hover": {
									cursor: "pointer",
								},
							}}
						>
							登录
						</Box>
					</Box>
				</Box>
			</Box>
		</>
	);
}
