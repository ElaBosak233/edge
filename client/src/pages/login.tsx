import { useUserApi } from "@/api/user";
import { useAuthStore } from "@/stores/auth";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useForm } from "@mantine/form";
import {
	showErrNotification,
	showSuccessNotification,
} from "@/utils/notification";
import { Box, TextInput, Button, ThemeIcon } from "@mantine/core";
import Icon from "@mdi/react";
import { mdiAccount, mdiLock } from "@mdi/js";

export default function Page() {
	const navigate = useNavigate();
	const userApi = useUserApi();
	const authStore = useAuthStore();

	useEffect(() => {
		document.title = "登录 - Edge";
	}, []);

	const [loginLoading, setLoginLoading] = useState(false);

	const form = useForm({
		mode: "uncontrolled",
		initialValues: {
			username: "",
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
		},
	});

	function login(username: string, password: string) {
		setLoginLoading(true);
		userApi
			.login(username, password)
			.then((res) => {
				const r = res.data;
				authStore.setPgsToken(r.token as string);
				authStore.setUser(r.data);
				showSuccessNotification({
					title: "登录成功",
					message: `欢迎进入 Edge`,
				});
				navigate("/");
			})
			.catch((err) => {
				switch (err?.response?.status) {
					case 400:
						showErrNotification({
							message: "用户名或密码错误",
						});
						break;
					default:
						showErrNotification({
							title: "发生了错误",
							message: `登录失败 ${err}`,
						});
						break;
				}
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
					sx={{
						display: "flex",
						flexDirection: "column",
						marginTop: "2rem",
					}}
				>
					<form
						onSubmit={form.onSubmit((values) =>
							login(values.username, values.password)
						)}
					>
						<TextInput
							label="用户名"
							size="lg"
							leftSection={
								<ThemeIcon variant={"transparent"}>
									<Icon path={mdiAccount} />
								</ThemeIcon>
							}
							key={form.key("username")}
							{...form.getInputProps("username")}
						/>
						<TextInput
							label="密码"
							type="password"
							size="lg"
							leftSection={
								<ThemeIcon variant={"transparent"}>
									<Icon path={mdiLock} />
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
							登录
						</Button>
					</form>
					<Box
						sx={{
							display: "flex",
							marginTop: "1rem",
							justifyContent: "end",
						}}
					>
						没有帐号？
						<Box
							onClick={() => navigate("/register")}
							sx={{
								fontStyle: "italic",
								":hover": {
									cursor: "pointer",
								},
							}}
						>
							注册
						</Box>
					</Box>
				</Box>
			</Box>
		</>
	);
}
