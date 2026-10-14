package com.ven.app.ui.screens.ai

import androidx.compose.runtime.Immutable

/**
 * Pengirim pesan dalam percakapan AI Assistant (DESIGN.md §8.12.6).
 */
enum class ChatSender {
    User,
    Ai,
}

/**
 * Status pengiriman pesan.
 */
enum class MessageStatus {
    Sent,
    Sending,
    Error,
}

/**
 * Model entitas satu gelembung/blok pesan percakapan.
 */
@Immutable
data class ChatMessage(
    val id: String,
    val sender: ChatSender,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: MessageStatus = MessageStatus.Sent,
)

/**
 * Model entitas satu sesi percakapan lampau untuk drawer riwayat ("Recents").
 */
@Immutable
data class ChatConversation(
    val id: String,
    val title: String,
    val lastUpdated: Long = System.currentTimeMillis(),
    val messages: List<ChatMessage> = emptyList(),
)

/**
 * State visual utama layar AI:
 * - Empty: watermark sparkle besar di tengah-atas, input pill di tengah kanvas vertikal, label "AI assistant".
 * - Conversation: daftar pesan percakapan scrollable dengan input pill melayang di dasar layar.
 */
enum class AiScreenState {
    Empty,
    Conversation,
}

/**
 * UI State untuk [AiChatScreen] mengikuti pola MVI (Unidirectional Data Flow).
 * Sesuai spesifikasi DESIGN.md §8.12.6.
 */
@Immutable
data class AiChatUiState(
    val currentChatId: String? = null,
    val messages: List<ChatMessage> = emptyList(),
    val inputText: String = "",
    val isAiTyping: Boolean = false,
    val chatHistory: List<ChatConversation> = emptyList(),
    val isDrawerOpen: Boolean = false,
    val errorMessage: String? = null,
) {
    /**
     * State tampilan layar saat ini: kosong jika belum ada pesan, atau percakapan aktif.
     */
    val screenState: AiScreenState
        get() = if (messages.isEmpty()) AiScreenState.Empty else AiScreenState.Conversation

    /**
     * Apakah pesan dapat dikirim: teks tidak kosong dan AI tidak sedang mengetik respons.
     */
    val canSend: Boolean
        get() = inputText.isNotBlank() && !isAiTyping
}
