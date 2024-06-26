import { Image, Box, Stack, Text } from "@mantine/core";

export default function Page() {
	return (
		<>
			<Box
				sx={{
					position: "fixed",
					top: "50%",
					left: "50%",
					zIndex: -1,
					transform: "translate(-50%, -50%)",
					width: "100%",
				}}
			>
				<Stack align={"center"}>
					<Text size="3rem">欢迎登录 Edge，你可以畅所欲言</Text>
					<Text size="2rem">
						Java 21（Spring MVC × Spring JPA），React 18（Typescript
						× Mantine），SQLite3
					</Text>
					<Text size="2rem">登录后即可使用所有功能</Text>
					<Text size="1rem">
						此项目仅仅是一个 Java
						实践周的作业，不会长期维护，仅供学习参考，并且安全性未经验证，请勿在生产环境使用
					</Text>
					<Image
						src={
							"https://p.sda1.dev/17/a67829effd62b4aff0fecf52f5609742/mlp.png"
						}
						w={512}
						h={300}
					/>
				</Stack>
			</Box>
		</>
	);
}
