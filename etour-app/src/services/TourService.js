import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { TOUR_SERVICE_BASE_URL } from "../constants/urls";
import {
  isJsonContentType,
  processError,
  processResponse,
} from "../utils/requestutils";
import { httpGet, httpPost, httpPatch } from "../constants/http";

export const tourAPI = createApi({
  reducerPath: "tourAPI",
  baseQuery: fetchBaseQuery({
    baseUrl: `${TOUR_SERVICE_BASE_URL}/tour`,
    credentials: "include",
    isJsonContentType,
  }),
  tagTypes: ["tour"],
  endpoints: (builder) => ({
    fetchAllToursByTourSubCategoryId: builder.query({
      query: (tourSubCategoryId) => ({
        url: `?tourSubcategoryId=${tourSubCategoryId}`,
        method: httpGet,
      }),
      keepUnusedDataFor: 120,
      transformResponse: processResponse,
      transformErrorResponse: processError,
      providesTags: () => ["tour"],
    }),
    addTourReview: builder.mutation({
      query: (newReview) => ({
        url: "/review/create",
        method: httpPost,
        body: newReview,
      }),
      transformResponse: processResponse,
      transformErrorResponse: processError,
    }),
    createTourBooking: builder.mutation({
      query: (newBooking) => ({
        url: "/booking/create",
        method: httpPost,
        body: newBooking,
      }),
      transformResponse: processResponse,
      transformErrorResponse: processError,
    }),
    fetchTourBookingById: builder.query({
      query: (bookingId) => ({
        url: `/booking/${bookingId}`,
        method: httpGet,
      }),
      transformResponse: processResponse,
      transformErrorResponse: processError,
    }),
    fetchTourBookingsByUserId: builder.query({
      query: (userId) => ({
        url: `/user/bookings/${userId}`,
        method: httpGet,
      }),
      transformResponse: processResponse,
      transformErrorResponse: processError,
    }),
    fetchAllPopularTours: builder.query({
      query: () => ({
        url: "/popular/list",
        method: httpGet,
      }),
      transformResponse: processResponse,
      transformErrorResponse: processError,
    }),
    fetchAllTours: builder.query({
      query: () => ({
        url: "/list",
        method: httpGet,
      }),
      keepUnusedDataFor: 120,
      transformResponse: processResponse,
      transformErrorResponse: processError,
      providesTags: () => ["tour"],
    }),
    fetchTourSuccessBooking: builder.query({
      query: (bookingReferenceId) => ({
        url: `/booking/success/${bookingReferenceId}`,
        method: httpPatch
      }),
      transformResponse: processResponse,
      transformErrorResponse: processError,
    })
  }),
});
