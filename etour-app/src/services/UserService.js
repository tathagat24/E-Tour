import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import {
  baseUrl,
  isJsonContentType,
  processError,
  processResponse,
} from "../utils/requestutils";
import { httpGet, httpPatch, httpPost } from "../constants/http";

export const userAPI = createApi({
  reducerPath: "userAPI",
  baseQuery: fetchBaseQuery({
    baseUrl,
    credentials: "include",
    isJsonContentType,
  }),
  tagTypes: ["User"],
  endpoints: (builder) => ({
    fetchUser: builder.query({
      query: () => ({
        url: "/profile",
        method: httpGet,
      }),
      keepUnusedDataFor: 120,
      transformResponse: processResponse,
      transformErrorResponse: processError,
      providesTags: () => ["User"],
    }),
    loginUser: builder.mutation({
      query: (credentials) => ({
        url: "/login",
        method: httpPost,
        body: credentials,
      }),
      transformResponse: processResponse,
      transformErrorResponse: processError,
    }),
    registerUser: builder.mutation({
      query: (registerRequest) => ({
        url: "/register",
        method: httpPost,
        body: registerRequest,
      }),
      transformResponse: processResponse,
      transformErrorResponse: processError,
    }),
    verifyAccount: builder.mutation({
      query: (key) => ({
        url: `/verify/account?key=${key}`,
        method: httpGet,
      }),
      transformResponse: processResponse,
      transformErrorResponse: processError,
    }),
    resetPassword: builder.mutation({
      query: (email) => ({
        url: "/resetpassword",
        method: httpPost,
        body: email,
      }),
      transformResponse: processResponse,
      transformErrorResponse: processError,
      invalidatesTags: (result, error) => (error ? [] : ["User"]),
    }),
    verifyPassword: builder.mutation({
      query: (key) => ({
        url: `/verify/password?key=${key}`,
        method: httpGet,
      }),
      transformResponse: processResponse,
      transformErrorResponse: processError,
      invalidatesTags: (result, error) => (error ? [] : ["User"]),
    }),
    doResetPassword: builder.mutation({
      query: (passwordRequest) => ({
        url: "/resetpassword/reset",
        method: httpPost,
        body: passwordRequest,
      }),
      transformResponse: processResponse,
      transformErrorResponse: processError,
      invalidatesTags: (result, error) => (error ? [] : ["User"]),
    }),
    updatePhoto: builder.mutation({
      query: (form) => ({
        url: "/photo",
        method: httpPatch,
        body: form,
      }),
      transformResponse: processResponse,
      transformErrorResponse: processError,
      invalidatesTags: (result, error) => (error ? [] : ["User"]),
    }),
    updateUser: builder.mutation({
      query: (user) => ({
        url: "/update",
        method: httpPatch,
        body: user,
      }),
      transformResponse: processResponse,
      transformErrorResponse: processError,
      invalidatesTags: (result, error) => (error ? [] : ["User"]),
    }),
    updatePassword: builder.mutation({
      query: (request) => ({
        url: "/updatepassword",
        method: httpPatch,
        body: request,
      }),
      transformResponse: processResponse,
      transformErrorResponse: processError,
      invalidatesTags: (result, error) => (error ? [] : ["User"]),
    }),
    toggleAccountExpired: builder.mutation({
      query: () => ({
        url: "/toggleaccountexpired",
        method: httpPatch,
      }),
      transformResponse: processResponse,
      transformErrorResponse: processError,
      invalidatesTags: (result, error) => (error ? [] : ["User"]),
    }),
    toggleAccountLocked: builder.mutation({
      query: () => ({
        url: "/toggleaccountlocked",
        method: httpPatch,
      }),
      transformResponse: processResponse,
      transformErrorResponse: processError,
      invalidatesTags: (result, error) => (error ? [] : ["User"]),
    }),
    toggleAccountEnabled: builder.mutation({
      query: () => ({
        url: "/toggleaccountenabled",
        method: httpPatch,
      }),
      transformResponse: processResponse,
      transformErrorResponse: processError,
      invalidatesTags: (result, error) => (error ? [] : ["User"]),
    }),
    updateRole: builder.mutation({
      query: (role) => ({
        url: "/updaterole",
        method: httpPatch,
        body: role,
      }),
      transformResponse: processResponse,
      transformErrorResponse: processError,
      invalidatesTags: (result, error) => (error ? [] : ["User"]),
    }),
    getUsers: builder.query({
      query: () => ({
        url: "/list",
        method: httpGet,
      }),
      transformResponse: processResponse,
      transformErrorResponse: processError,
    }),
    logout: builder.mutation({
      query: () => ({
        url: "/logout",
        method: httpPost,
      }),
      transformResponse: processResponse,
      transformErrorResponse: processError,
      invalidatesTags: () => ["User"],
    }),
  }),
});
