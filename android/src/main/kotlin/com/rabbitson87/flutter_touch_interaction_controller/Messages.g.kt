// Autogenerated from Pigeon (v10.1.0), do not edit directly.
// See also: https://pub.dev/packages/pigeon


import android.util.Log
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MessageCodec
import io.flutter.plugin.common.StandardMessageCodec
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

private fun wrapResult(result: Any?): List<Any?> {
  return listOf(result)
}

private fun wrapError(exception: Throwable): List<Any?> {
  if (exception is FlutterError) {
    return listOf(
      exception.code,
      exception.message,
      exception.details
    )
  } else {
    return listOf(
      exception.javaClass.simpleName,
      exception.toString(),
      "Cause: " + exception.cause + ", Stacktrace: " + Log.getStackTraceString(exception)
    )
  }
}

/**
 * Error class for passing custom error details to Flutter via a thrown PlatformException.
 * @property code The error code.
 * @property message The error message.
 * @property details The error details. Must be a datatype supported by the api codec.
 */
class FlutterError (
  val code: String,
  override val message: String? = null,
  val details: Any? = null
) : Throwable()

enum class IntentName(val raw: Int) {
  MOTIONACTION(0),
  X(1),
  Y(2),
  TOOLTYPE(3),
  EVENTTIME(4);

  companion object {
    fun ofRaw(raw: Int): IntentName? {
      return values().firstOrNull { it.raw == raw }
    }
  }
}

enum class EventChannelName(val raw: Int) {
  MOTION(0),
  REQUESTACCESSIBILITY(1),
  TOUCH(2),
  SWIPE(3);

  companion object {
    fun ofRaw(raw: Int): EventChannelName? {
      return values().firstOrNull { it.raw == raw }
    }
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class Point (
  val x: Double,
  val y: Double

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): Point {
      val x = list[0] as Double
      val y = list[1] as Double
      return Point(x, y)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      x,
      y,
    )
  }
}
@Suppress("UNCHECKED_CAST")
private object MessageApiCodec : StandardMessageCodec() {
  override fun readValueOfType(type: Byte, buffer: ByteBuffer): Any? {
    return when (type) {
      128.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          Point.fromList(it)
        }
      }
      else -> super.readValueOfType(type, buffer)
    }
  }
  override fun writeValue(stream: ByteArrayOutputStream, value: Any?)   {
    when (value) {
      is Point -> {
        stream.write(128)
        writeValue(stream, value.toList())
      }
      else -> super.writeValue(stream, value)
    }
  }
}

/** Generated interface from Pigeon that represents a handler of messages from Flutter. */
interface MessageApi {
  fun isAccessibilityPermissionEnabled(): Boolean
  fun requestAccessibilityPermission(): Boolean
  fun touch(point: Point): Boolean
  fun swipe(startPoint: Point, endPoint: Point): Boolean

  companion object {
    /** The codec used by MessageApi. */
    val codec: MessageCodec<Any?> by lazy {
      MessageApiCodec
    }
    /** Sets up an instance of `MessageApi` to handle messages through the `binaryMessenger`. */
    @Suppress("UNCHECKED_CAST")
    fun setUp(binaryMessenger: BinaryMessenger, api: MessageApi?) {
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.MessageApi.isAccessibilityPermissionEnabled", codec)
        if (api != null) {
          channel.setMessageHandler { _, reply ->
            var wrapped: List<Any?>
            try {
              wrapped = listOf<Any?>(api.isAccessibilityPermissionEnabled())
            } catch (exception: Throwable) {
              wrapped = wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.MessageApi.requestAccessibilityPermission", codec)
        if (api != null) {
          channel.setMessageHandler { _, reply ->
            var wrapped: List<Any?>
            try {
              wrapped = listOf<Any?>(api.requestAccessibilityPermission())
            } catch (exception: Throwable) {
              wrapped = wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.MessageApi.touch", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val pointArg = args[0] as Point
            var wrapped: List<Any?>
            try {
              wrapped = listOf<Any?>(api.touch(pointArg))
            } catch (exception: Throwable) {
              wrapped = wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.MessageApi.swipe", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val startPointArg = args[0] as Point
            val endPointArg = args[1] as Point
            var wrapped: List<Any?>
            try {
              wrapped = listOf<Any?>(api.swipe(startPointArg, endPointArg))
            } catch (exception: Throwable) {
              wrapped = wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
    }
  }
}
