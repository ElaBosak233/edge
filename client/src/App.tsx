import useTheme from "@/composables/useTheme";
import { Box, LoadingOverlay, MantineProvider } from "@mantine/core";
import routes from "~react-pages";
import { MantineEmotionProvider, emotionTransform } from "@mantine/emotion";
import { Notifications } from "@mantine/notifications";
import { ModalsProvider } from "@mantine/modals";
import { useRoutes } from "react-router-dom";
import { Suspense } from "react";
import Navbar from "./components/navigations/Navbar";

function App() {
	const { theme } = useTheme();

	return (
		<>
			<MantineProvider
				stylesTransform={emotionTransform}
				theme={theme}
				defaultColorScheme="light"
			>
				<MantineEmotionProvider>
					<ModalsProvider>
						<Navbar />
						<Box pt={64}>
							<Suspense fallback={<LoadingOverlay />}>
								{useRoutes(routes)}
							</Suspense>
						</Box>
						<Notifications zIndex={5000} />
					</ModalsProvider>
				</MantineEmotionProvider>
			</MantineProvider>
		</>
	);
}

export default App;
