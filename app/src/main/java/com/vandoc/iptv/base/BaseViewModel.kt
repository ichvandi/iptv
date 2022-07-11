package com.vandoc.iptv.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * @param A type of the Action
 * @param S type of the State
 * @param E type of the Event, this is used for single shot event
 *
 * @author Ichvandi
 * Created on 30/05/2022 at 20:35.
 */
abstract class BaseViewModel<A, S, E> : ViewModel() {

    private val _action = MutableSharedFlow<A>(extraBufferCapacity = 16)

    private val _state = MutableSharedFlow<S>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val state = _state.asSharedFlow()

    private val _event = Channel<E>()
    val event = _event.receiveAsFlow()

    init {
        _action.onEach(::handleAction).launchIn(viewModelScope)
    }

    protected abstract fun handleAction(action: A)

    fun setAction(action: A) = viewModelScope.launch {
        _action.tryEmit(action)
    }

    protected fun setState(state: S) = viewModelScope.launch {
        _state.tryEmit(state)
    }

    protected fun setEvent(event: E) = viewModelScope.launch {
        _event.send(event)
    }

}