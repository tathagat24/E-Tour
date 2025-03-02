import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { TOUR_SERVICE_BASE_URL } from "../constants/urls";
import {
  isJsonContentType,
  processError,
  processResponse,
} from "../utils/requestutils";
import { httpGet } from "../constants/http";

export const tourSubCategoryAPI = createApi({
  reducerPath: "tourSubCategoryAPI",
  baseQuery: fetchBaseQuery({
    baseUrl: `${TOUR_SERVICE_BASE_URL}/tour-subcategory`,
    credentials: "include",
    isJsonContentType,
  }),
  tagTypes: ["tourSubCategory"],
  endpoints: (builder) => ({
    fetchTourSubCategoriesByTourCategoryId: builder.query({
      query: (tourCategoryId) => ({
        url: `/tour-category/${tourCategoryId}`,
        method: httpGet,
      }),
      keepUnusedDataFor: 120,
      transformResponse: processResponse,
      transformErrorResponse: processError,
      providesTags: () => ["tourSubCategory"],
    }),
    fetchAllTourSubCategories: builder.query({
      query: () => ({
        url: "/list",
        method: httpGet,
      }),
      keepUnusedDataFor: 120,
      transformResponse: processResponse,
      transformErrorResponse: processError,
      providesTags: () => ["tourSubCategory"],
    })
  }),
});
