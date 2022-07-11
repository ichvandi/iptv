package com.vandoc.iptv.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * @param A type of the Action
 * @param S type of the State
 * @param E type of the Event, this is used for single shot event
 *
 * @author Ichvandi
 * Created on 30/05/2022 at 20:35.
 */
abstract class BaseComposeViewModel<A, S, E>(initialState: S) : ViewModel() {

    private val _action = MutableSharedFlow<A>(extraBufferCapacity = 16)

    var uiState by mutableStateOf(initialState)
        protected set

    private val _event = Channel<E>()
    val event = _event.receiveAsFlow()

    init {
        _action.onEach(::handleAction).launchIn(viewModelScope)
    }

    protected abstract fun handleAction(action: A)

    fun setAction(action: A) = viewModelScope.launch {
        _action.tryEmit(action)
    }

    protected fun setState(block: S.() -> S) = viewModelScope.launch {
        uiState = block(uiState)
    }

    protected fun setEvent(event: E) = viewModelScope.launch {
        _event.send(event)
    }

}