package util

import java.nio.ByteBuffer
import java.time.{LocalDate, LocalDateTime, LocalTime}

import boopickle.Default._
import io.circe.Json

object BinarySerializers {
  implicit val jsonPickler: Pickler[Json] = transformPickler((s: String) => util.JsonSerializers.parseJson(s).right.get)(x => x.spaces2)
  implicit val ldPickler: Pickler[LocalDate] = transformPickler((s: String) => util.DateUtils.fromDateString(s))(_.toString)
  implicit val ltPickler: Pickler[LocalTime] = transformPickler((s: String) => util.DateUtils.fromTimeString(s))(_.toString)
  implicit val ldtPickler: Pickler[LocalDateTime] = transformPickler((t: Long) => util.DateUtils.fromMillis(t))(x => util.DateUtils.toMillis(x))

  private[this] def toByteArray(bb: ByteBuffer) = {
    val arr = new Array[Byte](bb.remaining)
    bb.get(arr)
    arr
  }

}
