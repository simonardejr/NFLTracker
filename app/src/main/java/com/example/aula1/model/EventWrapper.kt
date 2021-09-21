package com.example.aula1.model

open class EventWrapper<out T>(private val content: T) {
    var hasBeenHandled = false
        private set // Permite a leitura, mas nao a escrita

    // Retorna o conteudo e previne de ser usado novamente
    fun getContentIfNotHandled(): T? {
        return if(hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    // Retorna o conteudo, mesmo se já foi manipulado
    fun peekContent(): T = content
}