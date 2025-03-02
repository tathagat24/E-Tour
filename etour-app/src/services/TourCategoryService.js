import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { TOUR_SERVICE_BASE_URL } from "../constants/urls";
import {
  isJsonContentType,
  processError,
  processResponse,
} from "../utils/requestutils";
import { httpGet } from "../constants/http";

export const tourCategoryAPI = createApi({
  reducerPath: "tourCategoryAPI",
  baseQuery: fetchBaseQuery({
    baseUrl: `${TOUR_SERVICE_BASE_URL}/tour-category`,
    credentials: "include",
    isJsonContentType,
  }),
  tagTypes: ["tourCategory"],
  endpoints: (builder) => ({
    fetchTourCategories: builder.query({
      query: () => ({
        url: "/list",
        method: httpGet,
      }),
      keepUnusedDataFor: 120,
      transformResponse: processResponse,
      transformErrorResponse: processError,
      providesTags: () => ["tourCategory"],
    }),
  }),
});
