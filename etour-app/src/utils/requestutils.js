import { LOGGEDIN } from "../constants/cache.key";
import { toastError, toastSuccess } from "../services/ToastService";

export const baseUrl = "http://localhost:8085/api/v1/user";

export const isJsonContentType = (headers) =>
  [
    "application/vnd.api+json",
    "application/json",
    "application/vnd.hal+json",
    "application/pdf",
    "multipart/form-data",
  ].includes(headers.get("Content-Type")?.trimEnd());

export const processResponse = (response, meta) => {
  const { request } = meta;

  if (request?.url?.includes("logout")) {
    localStorage.removeItem(LOGGEDIN);
  }

  if (!request?.url?.includes("profile")) {
    toastSuccess(response.message);
  }
  return response;
};

export const processError = (error) => {
  if (
    error?.data?.code === 401 &&
    error.data.status === "UNAUTHORIZED" &&
    error.data.message === "You are not logged in"
  ) {
    localStorage.setItem(LOGGEDIN, "false");
  }
  if (error.data.message === "You are not logged in") {
    return error;
  }
  toastError(error.data.message);
  console.log({ error });
  return error;
};
