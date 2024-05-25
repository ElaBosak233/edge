import { notifications, showNotification } from "@mantine/notifications";
import {
	mdiCheck,
	mdiClose,
	mdiExclamation,
	mdiInformationBoxOutline,
	mdiInformationOutline,
	mdiInformationSymbol,
} from "@mdi/js";
import Icon from "@mdi/react";

export function showErrNotification({
	id,
	title,
	message,
}: {
	id?: string;
	title?: string;
	message?: string;
}) {
	if (id) {
		notifications.update({
			id: id,
			title: title || "发生了错误",
			message: message,
			color: "red",
			icon: <Icon path={mdiClose} />,
			autoClose: 2000,
			withCloseButton: true,
			loading: false,
		});
		return;
	}
	showNotification({
		title: title || "发生了错误",
		message: message,
		color: "red",
		icon: <Icon path={mdiClose} />,
	});
}

export function showSuccessNotification({
	id,
	title,
	message,
}: {
	id?: string;
	title?: string;
	message?: string;
}) {
	if (id) {
		notifications.update({
			id: id,
			title: title || "成功",
			message: message,
			color: "green",
			icon: <Icon path={mdiCheck} />,
			autoClose: 2000,
			withCloseButton: true,
			loading: false,
		});
		return;
	}
	showNotification({
		title: title || "成功",
		message: message,
		color: "green",
		icon: <Icon path={mdiCheck} />,
	});
}

export function showInfoNotification({
	id,
	title,
	message,
}: {
	id?: string;
	title?: string;
	message?: string;
}) {
	if (id) {
		notifications.update({
			id: id,
			title: title || "信息",
			message: message,
			color: "blue",
			icon: <Icon path={mdiInformationOutline} />,
			autoClose: 2000,
			withCloseButton: true,
			loading: false,
		});
		return;
	}
	showNotification({
		title: title || "信息",
		message: message,
		color: "blue",
		icon: <Icon path={mdiInformationSymbol} />,
	});
}

export function showWarnNotification({
	id,
	title,
	message,
}: {
	id?: string;
	title?: string;
	message?: string;
}) {
	if (id) {
		notifications.update({
			id: id,
			title: title || "警告",
			message: message,
			color: "orange",
			icon: <Icon path={mdiExclamation} />,
			autoClose: 2000,
			withCloseButton: true,
			loading: false,
		});
		return;
	}
	showNotification({
		title: title || "警告",
		message: message,
		color: "orange",
		icon: <Icon path={mdiExclamation} />,
	});
}

export function showLoadingNotification({
	title,
	message,
}: {
	title?: string;
	message?: string;
}): string {
	const id = notifications.show({
		title: title || "请稍后",
		loading: true,
		message: message,
		color: "blue",
		autoClose: false,
		withCloseButton: false,
	});
	return id;
}
