package com.vandoc.iptv.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.Window
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.vandoc.iptv.BuildConfig
import com.vandoc.iptv.base.Resource
import com.vandoc.iptv.data.model.response.NetworkResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import retrofit2.Response
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.URL
import java.net.UnknownHostException

inline fun <reified T : Any> networkCall(crossinline block: suspend () -> Response<T>): Flow<Resource<T>> {
    return flow {
        try {
            emit(Resource.Loading)

            val response = block()
            when (response.code()) {
                in 200..226,
                in 300..308 -> {
                    emit(Resource.Success(response.body()))
                }
                in 400..451 -> {
                    val error = response.getError()
                    Timber.e(error.toString())
                    emit(Resource.Error.BadRequest(error.message))
                }
                in 500..511 -> {
                    val error = response.getError()
                    Timber.e(error.toString())

                    if (BuildConfig.DEBUG) {
                        emit(Resource.Error.ServerError(error.message))
                    } else {
                        emit(Resource.Error.ServerError("Sorry, there was an error on the server"))
                    }
                }
                else -> throw IllegalArgumentException("Unknown HTTP Status Code: ${response.code()}")
            }
        } catch (e: SocketTimeoutException) {
            Timber.e(e)
            emit(Resource.Error.ConnectionTimeout("The connection has timed out"))
        } catch (e: Exception) {
            when (e) {
                is ConnectException, is UnknownHostException -> {
                    Timber.e(e)
                    emit(Resource.Error.ConnectionError("Unable to connect to server, please check your internet connection"))
                }
                else -> throw e
            }
        } catch (e: Exception) {
            Timber.e(e)
            emit(Resource.Error.Unknown("Sorry, an unexpected error occurred"))
        }
    }
}

fun Response<*>.getError(): ErrorResponse {
    return try {
        Gson().fromJson(errorBody()?.charStream()?.readText(), ErrorResponse::class.java)
    } catch (e: Exception) {
        Timber.e(e)
        ErrorResponse(-1, "Unknown Error: Can't parse error message")
    }
}

inline fun <T, R> Flow<Resource<T>>.mapResource(crossinline mapper: (value: T?) -> R): Flow<Resource<R>> =
    transform { resource ->
        return@transform when (resource) {
            is Resource.Success -> emit(Resource.Success(mapper(resource.data)))
            is Resource.Error.BadRequest -> emit(resource)
            is Resource.Error.ConnectionError -> emit(resource)
            is Resource.Error.ConnectionTimeout -> emit(resource)
            is Resource.Error.ServerError -> emit(resource)
            is Resource.Error.Unknown -> emit(resource)
            Resource.Loading -> emit(Resource.Loading)
        }
    }

@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}

@Composable
fun StatusBar(shouldShow: Boolean, window: Window? = findWindow()) {
    if (window == null) return

    val controller = WindowInsetsControllerCompat(window, window.decorView)
    if (!shouldShow) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controller.hide(WindowInsetsCompat.Type.systemBars())
    }

    DisposableEffect(Unit) {
        onDispose {
            WindowCompat.setDecorFitsSystemWindows(window, true)
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE
            controller.show(WindowInsetsCompat.Type.systemBars())
        }
    }
}

@Composable
private fun findWindow(): Window? =
    (LocalView.current.parent as? DialogWindowProvider)?.window
        ?: LocalView.current.context.findWindow()

private tailrec fun Context.findWindow(): Window? =
    when (this) {
        is Activity -> window
        is ContextWrapper -> baseContext.findWindow()
        else -> null
    }

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Composable
inline fun <reified T> Flow<T>.observeWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: (T) -> Unit
) {
    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycleScope.launch {
            flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect(action)
        }
    }
}

val TOOLBAR_HEIGHT_IN_DP = 56.dp

fun getNetworkDetails(): NetworkResponse {
    return Gson().fromJson(String(
        URL("https://ifconfig.co/json")
            .openConnection()
            .apply { connect() }
            .getInputStream()
            .readBytes()
    ), NetworkResponse::class.java)
}

fun CollapsingToolbarScaffoldState.getDynamicSize(collapsedState: Int, expandedState: Int): Float {
    return (collapsedState + (expandedState - collapsedState) * toolbarState.progress)
}

fun <T, R> networkBoundResource(
    onQuery: (suspend () -> R)? = null,
    onFetch: (suspend () -> Flow<Resource<T>>)? = null,
    onMapping: ((T?) -> R?)? = null,
    onResult: (suspend (Resource<R?>) -> Unit)? = null
): Flow<Resource<R>> {
    return flow {
        emit(Resource.Loading)

        val local = onQuery?.invoke()
        if (local != null) {
            if (local is List<*> && local.isNotEmpty()) {
                emit(Resource.Success(local))
                return@flow
            }

            if (local !is List<*>) {
                emit(Resource.Success(local))
                return@flow
            }
        }

        onFetch?.invoke()
            ?.flowOn(Dispatchers.IO)
            ?.mapResource { onMapping?.invoke(it) }
            ?.collect { onResult?.invoke(it) }

        emit(Resource.Success(onQuery?.invoke()))
    }
}

fun <T> debounce(
    waitMs: Long = 300L,
    scope: CoroutineScope,
    onDebounce: ((T) -> Unit)? = null,
): (T) -> Job? {
    var debounceJob: Job? = null
    return { param: T ->
        debounceJob?.cancel()
        debounceJob = scope.launch {
            delay(waitMs)
            onDebounce?.invoke(param)
        }
        debounceJob
    }
}
