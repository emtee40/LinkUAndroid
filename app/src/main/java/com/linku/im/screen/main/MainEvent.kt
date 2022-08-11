package com.linku.im.screen.main

sealed class MainEvent {
    data class CreateConversation(
        val type: Int,
        val name: String
    ) : MainEvent()

    object GetConversations : MainEvent()
}