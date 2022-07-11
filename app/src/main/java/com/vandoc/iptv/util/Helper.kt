package com.vandoc.iptv.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptionsBuilder
import com.google.gson.Gson
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vandoc.iptv.BuildConfig
import com.vandoc.iptv.base.AppDispatcher
import com.vandoc.iptv.base.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
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

inline fun <T, R> Flow<Resource<T>>.mapResource(
    dispatcher: AppDispatcher,
    crossinline mapper: (value: T?) -> R
): Flow<Resource<R>> =
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
    }.flowOn(dispatcher.default)

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun provideDestinationNavigator(): DestinationsNavigator = object : DestinationsNavigator {
    override fun clearBackStack(route: String): Boolean = true

    override fun navigate(
        route: String,
        onlyIfResumed: Boolean,
        builder: NavOptionsBuilder.() -> Unit
    ) = Unit

    override fun navigateUp(): Boolean = true

    override fun popBackStack(): Boolean = true

    override fun popBackStack(route: String, inclusive: Boolean, saveState: Boolean): Boolean = true
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
