package com.ven.app.data.api

import com.google.gson.annotations.SerializedName
import com.ven.app.data.api.models.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Model data request dan response untuk API AI Assistant (DESIGN.md §8.12.6).
 * Endpoint Laravel: POST /api/ai/tanya (throttled 10/min, Sanctum auth).
 */
data class AiChatRequest(
    @SerializedName("pertanyaan")
    val pertanyaan: String,
)

data class AiChatResponse(
    @SerializedName("jawaban")
    val jawaban: String? = null,
)

interface ApiAiChatService {
    @POST("ai/tanya")
    suspend fun tanyaAi(
        @Body request: AiChatRequest,
    ): Response<ApiResponse<AiChatResponse>>
}

object ApiAiChat {
    private val service: ApiAiChatService by lazy {
        ApiClient.createService(ApiAiChatService::class.java)
    }

    suspend fun tanya(pertanyaan: String): Result<String> {
        return try {
            val response = service.tanyaAi(AiChatRequest(pertanyaan = pertanyaan.trim()))
            if (response.isSuccessful && response.body()?.data?.jawaban != null) {
                Result.success(response.body()!!.data!!.jawaban!!)
            } else if (response.code() == 429) {
                Result.failure(Exception("Batas permintaan tercapai. Silakan coba beberapa saat lagi."))
            } else {
                val errorMsg = ApiClient.parseError(response.errorBody()?.string())
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(Exception(e.localizedMessage ?: "Tidak dapat terhubung ke asisten AI"))
        }
    }
}
